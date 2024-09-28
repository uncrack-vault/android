package com.geekymusketeers.uncrack.presentation.intro.model

import com.geekymusketeers.uncrack.R

data class OnBoardingItem(
    val title: String,
    val text: String,
    val image: Int
) {
    companion object {
        fun onboardingScreenItems() = listOf(
            OnBoardingItem(
                "Manage All Passwords",
                "UnCrack is offline and we do not store your data on our server. Your data is saved offline on your device and you can sync your data in your online cloud.",
                R.drawable.manage_all_password
            ),
            OnBoardingItem(
                "We Store For You",
                "We store your logins, IDs, and important information in your vault. Just remember a master password and forget other things and its offline.",
                R.drawable.we_store_for_you
            ),
            OnBoardingItem(
                "Lock Your Files",
                "You can lock your files based on priority that is set by you. Brigade supports type based file security maintenance.",
                R.drawable.lock_your_file
            ),
        )
    }
}

