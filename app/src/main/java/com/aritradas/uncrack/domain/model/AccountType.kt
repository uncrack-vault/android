package com.aritradas.uncrack.domain.model

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
    SIGNAL("Signal"),
    LINE("Line"),
    WECHAT("WeChat"),
    SKYPE("Skype"),
    ZOOM("Zoom"),
    STACKOVERFLOW("Stack Overflow"),
    MEDIUM("Medium"),
    GITHUB("GitHub"),
    GITLAB("GitLab"),
    MICROSOFTONEDRIVE("Microsoft OneDrive"),
    DROPBOX("DropBox"),
    GOOGLEDRIVE("Google Drive"),
    DRIBBLE("Dribble"),
    BEHANCE("Behance"),
    REDDIT("Reddit"),
    MEETUP("Meetup"),
    PINTEREST("Pinterest"),
    OTHER("Other");

    companion object {
        fun getAccountTypeFromString(accountName: String): AccountType {
            return entries.find { it.text.equals(accountName, ignoreCase = true) } ?: OTHER
        }
    }
}