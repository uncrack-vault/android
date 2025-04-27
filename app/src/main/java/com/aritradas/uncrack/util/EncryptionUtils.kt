package com.aritradas.uncrack.util

import android.content.Context
import android.util.Base64
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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
    private const val KEY_DATA = "encryption_key_data"

    private lateinit var secretKey: SecretKey
    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return

        secretKey = getOrCreateKey(context)
        isInitialized = true
    }

    private fun getOrCreateKey(context: Context): SecretKey {
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

        // Check if we have a stored key
        val keyString = sharedPreferences.getString(KEY_DATA, null)

        return if (keyString != null) {
            // If key exists, restore it
            val keyBytes = Base64.decode(keyString, Base64.DEFAULT)
            SecretKeySpec(keyBytes, "AES")
        } else {
            // Generate a new key if none exists
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256)
            val newKey = keyGenerator.generateKey()

            // Store the new key
            val keyBytes = newKey.encoded
            val keyString = Base64.encodeToString(keyBytes, Base64.DEFAULT)
            sharedPreferences.edit { putString(KEY_DATA, keyString) }

            newKey
        }
    }

    fun encrypt(data: String): String {
        if (!isInitialized) {
            throw IllegalStateException("EncryptionUtils must be initialized before use")
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

            return Base64.encodeToString(combined, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return data // Return original data in case of encryption failure
        }
    }

    fun decrypt(encryptedData: String): String {
        if (!isInitialized) {
            throw IllegalStateException("EncryptionUtils must be initialized before use")
        }

        try {
            val combined = Base64.decode(encryptedData, Base64.DEFAULT)

            // Extract IV and encrypted data
            val iv = combined.copyOfRange(0, 12) // GCM uses 12 bytes IV
            val encrypted = combined.copyOfRange(12, combined.size)

            val cipher = Cipher.getInstance(ALGORITHM)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)

            val decryptedBytes = cipher.doFinal(encrypted)
            return String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return encryptedData // Return encrypted data in case of decryption failure
        }
    }
}