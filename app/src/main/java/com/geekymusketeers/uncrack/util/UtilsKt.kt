package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import java.util.regex.Pattern

object UtilsKt {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun validateEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && email.split("")
            .all { word ->
                word.isNotEmpty() && Pattern.matches("^([A-Za-z.]+)*", word)
            }
    }

    fun calculatePasswordStrength(password: String): Float {
        val length = password.length
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        var score = 0
        if (length >= 8) score++
        if (hasUpperCase) score++
        if (hasLowerCase) score++
        if (hasDigit) score++

        return score.toFloat() / 4f
    }
}