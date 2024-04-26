package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.domain.model.AccountType
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

    fun calculatePasswordStrength(password: String): Int {
        var score = 0
        // Check for password length
        if (password.length >= 8) {
            score += 1
        }
        // Check for uppercase letters
        if (password.matches(Regex(".*[A-Z].*"))) {
            score += 1
        }
        // Check for lowercase letters
        if (password.matches(Regex(".*[a-z].*"))) {
            score += 1
        }
        // Check for digits
        if (password.matches(Regex(".*\\d.*"))) {
            score += 1
        }
        // Check for special characters
        if (password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))) {
            score += 1
        }
        // Check for consecutive characters
        if (!password.matches(Regex("(.)\\1{2,}"))) {
            score += 1
        }
        // Calculate the password score out of 9
        return ((score.toFloat() / 6.toFloat()) * 9).toInt()
    }

    fun generateRandomPassword(length: Int): String {
        val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun getSocialAccounts(): Map<AccountType, Int> {
        return mapOf(
            AccountType.INSTAGRAM to R.drawable.instagram,
            AccountType.FACEBOOK to R.drawable.facebook,
            AccountType.LINKEDIN to R.drawable.linkedin,
            AccountType.SNAPCHAT to R.drawable.snapchat,
            AccountType.TWITTER to R.drawable.twitter,
            AccountType.MESSENGER to R.drawable.messenger
        )
    }
    fun getCrowdSourcingAccounts(): Map<AccountType, Int> {
        return mapOf(
            AccountType.BUYMEACOFFEE to R.drawable.new_buy_me_a_coffee,
            AccountType.PATREON to R.drawable.patreon,
        )
    }
    fun getCommunicationAccounts(): Map<AccountType, Int> {
        return mapOf(
            AccountType.SLACK to R.drawable.slack,
            AccountType.TELEGRAM to R.drawable.telegram,
            AccountType.DISCORD to R.drawable.new_discord,
        )
    }
    fun getPortfolioAccounts(): Map<AccountType, Int> {
        return mapOf(
            AccountType.DRIBBLE to R.drawable.dribbble,
            AccountType.BEHANCE to R.drawable.new_behance,
        )
    }
    fun getCommunitiesAccounts(): Map<AccountType, Int> {
        return mapOf(
            AccountType.REDDIT to R.drawable.cl_reddit,
            AccountType.MEETUP to R.drawable.meetup,
            AccountType.PINTEREST to R.drawable.pinterest
        )
    }
}