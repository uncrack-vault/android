package com.geekymusketeers.uncrack.util

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object EncryptionUtils {
    // Generate a secret key
    @RequiresApi(Build.VERSION_CODES.M)
    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES)
        keyGenerator.init(KeyGenParameterSpec.Builder("MyKeyAlias", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build())
        return keyGenerator.generateKey()
    }

    // Encrypt a string with the secret key
    fun encryptString(stringToEncrypt: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(stringToEncrypt.toByteArray(StandardCharsets.UTF_8))
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }
}





