package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.domain.model.AccountType
import java.util.regex.Pattern

object UtilsKt {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun validateName(name: String?): Boolean {
        return !name.isNullOrEmpty() &&
                name.split(" ")
                    .all { word -> word.isNotEmpty() && Pattern.matches("^([A-Za-z.]+)*", word) }
    }

    fun validatePassword(password: String): Boolean {
        // Check if the password is empty
        if (password.isEmpty()) {
            return false
        }

        // Check if the password is at least 8 characters long
        if (password.length < 8) {
            return false
        }

        // Check if the password contains at least one uppercase letter
        if (!password.contains(Regex("[A-Z]"))) {
            return false
        }

        // Check if the password contains at least one lowercase letter
        if (!password.contains(Regex("[a-z]"))) {
            return false
        }

        // Check if the password contains at least one digit
        if (!password.contains(Regex("\\d"))) {
            return false
        }

        // Check if the password contains at least one special character
        if (!password.contains(Regex("[^A-Za-z0-9]"))) {
            return false
        }

        // Check if the password does not contain any whitespace
        if (password.contains(" ")) {
            return false
        }

        return true
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

    @Composable
    fun getAccountImage(accountName: String): Painter {
        return when (accountName) {
            "Instagram" -> painterResource(id = R.drawable.instagram)
            "Facebook" -> painterResource(id = R.drawable.facebook)
            "LinkedIn" -> painterResource(id = R.drawable.linkedin)
            "Snapchat" -> painterResource(id = R.drawable.snapchat)
            "Twitter" -> painterResource(id = R.drawable.twitter)
            "Messenger" -> painterResource(id = R.drawable.messenger)
            "Buy Me a Coffee" -> painterResource(id = R.drawable.new_buy_me_a_coffee)
            "Patreon" -> painterResource(id = R.drawable.patreon)
            "Slack" -> painterResource(id = R.drawable.slack)
            "Telegram" -> painterResource(id = R.drawable.telegram)
            "Discord" -> painterResource(id = R.drawable.new_discord)
            "Dribble" -> painterResource(id = R.drawable.dribbble)
            "Behance" -> painterResource(id = R.drawable.new_behance)
            "Reddit" -> painterResource(id = R.drawable.cl_reddit)
            "Meetup" -> painterResource(id = R.drawable.meetup)
            "Pinterest" -> painterResource(id = R.drawable.pinterest)
            else -> painterResource(id = R.drawable.uncrack_logo)
        }
    }

    fun calculateAllPasswordsScore(password: String): Int {

        var score = 0

        // Check for password length
        if (password.length >= 8) {
            score += 10
        }

        // Check for uppercase letters
        if (password.matches(Regex(".*[A-Z].*"))) {
            score += 10
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

        // Calculate the password score out of 100
        return (score / 6) * 100
    }
}