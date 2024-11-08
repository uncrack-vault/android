package com.aritradas.uncrack.util

import org.mindrot.jbcrypt.BCrypt

object HashUtils {

    fun hasPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun checkHashPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}