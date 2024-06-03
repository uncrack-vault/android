package com.geekymusketeers.uncrack.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun generateAESKey(keySize: Int = 256): SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(keySize)
    return keyGenerator.generateKey()
}

fun aesEncrypt(data: ByteArray, secretKey: SecretKey): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16))
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(data)
}

fun aesDecrypt(encryptedData: ByteArray, secretKey: SecretKey): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16))
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(encryptedData)
}

@RequiresApi(Build.VERSION_CODES.O)
fun SecretKey.toBase64String(): String {
    return Base64.getEncoder().encodeToString(this.encoded)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toSecretKey(): SecretKey {
    val decodedKey = Base64.getDecoder().decode(this)
    return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
}
