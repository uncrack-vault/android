package com.geekymusketeers.uncrack.presentation.account

import com.geekymusketeers.uncrack.R

// TODO: Need to change icons for some of the items
enum class AccountItems(val itemsName: String, val icon: Int) {
    // Security
    CHANGE_MASTER_KEY("Change master password", R.drawable.lock),
    BIOMETRIC("Unlock with biometric", R.drawable.fingerprint),
    BLOCK_SS("Block screenshots", R.drawable.block_ss),

    //BACKUP
    EXPORT_IMPORT("Export/Import", R.drawable.import_export),
    BACKUP_RESTORE("Backup/Restore", R.drawable.backup_restore),

    // General
    THEME("Theme", R.drawable.themes),
    PASSWORD_GENERATOR("Password Generator", R.drawable.generate_password_icon),
    INVITE_FRIENDS("Invite Friends", R.drawable.share_app),
    RATE_UNCRACK("Rate UnCrack", R.drawable.rating),
    SEND_FEEDBACK("Send Feedback", R.drawable.feedback),
    PRIVACY_POLICY("Privacy Policy", R.drawable.shield),

    // Danger Zone
    LOG_OUT("Log out", R.drawable.logout),
    DELETE_ACCOUNT("Delete Account", R.drawable.delete_icon)
}