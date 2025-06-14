package com.aritradas.uncrack.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import timber.log.Timber
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH = 128
    private const val KEY_ALIAS = "uncrack_encryption_key"
    private const val PREFERENCES_FILE = "encryption_prefs"
    private const val FALLBACK_PREFS_FILE = "fallback_encryption_prefs"
    private const val KEY_DATA = "encryption_key_data"

    private lateinit var secretKey: SecretKey

    @Volatile
    private var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            synchronized(this) {
                if (!isInitialized) {
                    try {
                        secretKey = getOrCreateSecureKey(context)
                    } catch (e: Exception) {
                        Timber.e(
                            e,
                            "Error initializing secure encryption, falling back to regular encryption"
                        )
                        secretKey = getOrCreateFallbackKey(context)
                    }
                    isInitialized = true
                }
            }
        }
    }

    private fun getOrCreateSecureKey(context: Context): SecretKey {
        try {
            // Create or get the master key for EncryptedSharedPreferences
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // Get encrypted shared preferences
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFERENCES_FILE,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val keyString = sharedPreferences.getString(KEY_DATA, null)
            return if (keyString != null) {
                val keyBytes = android.util.Base64.decode(keyString, android.util.Base64.DEFAULT)
                SecretKeySpec(keyBytes, "AES")
            } else {
                generateAndStoreNewKey(sharedPreferences)
            }
        } catch (e: Exception) {
            throw e  // Rethrow to be handled by the fallback in initialize()
        }
    }

    private fun getOrCreateFallbackKey(context: Context): SecretKey {
        val sharedPreferences =
            context.getSharedPreferences(FALLBACK_PREFS_FILE, Context.MODE_PRIVATE)
        val keyString = sharedPreferences.getString(KEY_DATA, null)

        return if (keyString != null) {
            val keyBytes = android.util.Base64.decode(keyString, android.util.Base64.DEFAULT)
            SecretKeySpec(keyBytes, "AES")
        } else {
            generateAndStoreNewKey(sharedPreferences)
        }
    }

    private fun generateAndStoreNewKey(sharedPreferences: SharedPreferences): SecretKey {
        // Generate a new key
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val newKey = keyGenerator.generateKey()

        // Store the new key
        val keyBytes = newKey.encoded
        val keyString = android.util.Base64.encodeToString(keyBytes, android.util.Base64.DEFAULT)
        sharedPreferences.edit { putString(KEY_DATA, keyString) }

        return newKey
    }

    fun encrypt(data: String): String {
        if (!isInitialized) {
            throw IllegalStateException("EncryptionUtils must be initialized before use")
        }

        // Handle empty input
        if (data.isEmpty()) {
            return ""
        }

        try {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))

            // Combine IV and encrypted data
            val combined = ByteArray(iv.size + encryptedBytes.size)
            System.arraycopy(iv, 0, combined, 0, iv.size)
            System.arraycopy(encryptedBytes, 0, combined, iv.size, encryptedBytes.size)

            return android.util.Base64.encodeToString(combined, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            Timber.e(e, "Encryption error for data: ${data.take(20)}...")
            throw IllegalStateException(
                "Failed to encrypt data",
                e
            ) // Throw exception instead of returning original data
        }
    }

    fun decrypt(encryptedData: String): String {
        if (!isInitialized) {
            throw IllegalStateException("EncryptionUtils must be initialized before use")
        }

        // Handle empty or null input
        if (encryptedData.isEmpty()) {
            return ""
        }

        try {
            val combined = android.util.Base64.decode(encryptedData, android.util.Base64.DEFAULT)

            // Validate combined data length
            if (combined.size < 12) {
                Timber.e("Invalid encrypted data: too short")
                return "" // Return empty string for invalid data
            }

            // Extract IV and encrypted data
            val iv = combined.copyOfRange(0, 12) // GCM uses 12 bytes IV
            val encrypted = combined.copyOfRange(12, combined.size)

            val cipher = Cipher.getInstance(ALGORITHM)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)

            val decryptedBytes = cipher.doFinal(encrypted)
            return String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            Timber.e(e, "Decryption error for data: ${encryptedData.take(20)}...")
            return "" // Return empty string instead of encrypted data when decryption fails
        }
    }

    /**
     * Utility function to check if a string appears to be encrypted data
     * This can help identify corrupted or unencrypted data in the database
     */
    fun isLikelyEncryptedData(data: String): Boolean {
        if (data.isEmpty()) return false

        return try {
            // Check if it's valid Base64 and has reasonable length for encrypted data
            val decoded = android.util.Base64.decode(data, android.util.Base64.DEFAULT)
            decoded.size >= 12 // At least IV size
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Force re-initialization of the encryption key
     * Use this only for recovery scenarios
     */
    fun reinitialize(context: Context) {
        isInitialized = false
        initialize(context)
    }
}
