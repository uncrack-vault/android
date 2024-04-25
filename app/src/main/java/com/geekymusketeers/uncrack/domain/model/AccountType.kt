package com.geekymusketeers.uncrack.domain.model

enum class AccountType(val text: String) {
    INSTAGRAM("Instagram"),
    FACEBOOK("Facebook"),
    LINKEDIN("LinkedIn"),
    SNAPCHAT("Snapchat"),
    TWITTER("Twitter"),
    MESSENGER("Messenger"),
    BUYMEACOFFEE("Buy Me a Coffee"),
    PATREON("Patreon"),
    SLACK("Slack"),
    TELEGRAM("Telegram"),
    DISCORD("Discord"),
    DRIBBLE("Dribble"),
    BEHANCE("Behance"),
    OTHER("Other");

    companion object {

        fun getAccountTypeFromString(accountName: String): AccountType {
            return when (accountName) {
                "Instagram" -> {
                    INSTAGRAM
                }
                "Facebook" -> {
                    FACEBOOK
                }
                "LinkedIn" -> {
                    LINKEDIN
                }
                "Snapchat" -> {
                    SNAPCHAT
                }
                "Twitter" -> {
                    TWITTER
                }
                "Messenger" -> {
                    MESSENGER
                }

                else -> {
                    OTHER
                }
            }
        }
    }
}