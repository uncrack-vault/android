package com.geekymusketeers.uncrack.presentation.accountScreen

import com.geekymusketeers.uncrack.R

// TODO: Need to change icons for some of the items
enum class AccountItems(val itemsName: String, val icon: Int) {
    MASTER_KEY("Master Key", R.drawable.lock),
    BIOMETRIC("Biometric", R.drawable.fingerprint),
    THEME("Theme", R.drawable.themes),
    INVITE_FRIENDS("Invite Friends", R.drawable.share_app),
    RATE_UNCRACK("Rate UnCrack", R.drawable.rating),
    SEND_FEEDBACK("Send Feedback", R.drawable.feedback),
    PRIVACY_POLICY("Privacy Policy", R.drawable.shield),
    LOG_OUT("Log out", R.drawable.logout),
    DELETE_ACCOUNT("Delete Account", R.drawable.delete_icon)
}