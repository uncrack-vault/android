package com.geekymusketeers.uncrack.presentation.intro.model

import com.geekymusketeers.uncrack.R

data class OnBoardingItem(
    val title: String,
    val text: String,
    val image: Int
) {
    companion object {
        // TODO: Need to fix the strings
        fun onboardingScreenItems() = listOf(
            OnBoardingItem(
                "Control your security",
                "Subtext",
                R.drawable.logo_uncrack
            ),
            OnBoardingItem(
                "Everything in a single click",
                "Subtext",
                R.drawable.logo_uncrack
            ),
            OnBoardingItem(
                "Safe & Secure with us",
                "Subtext",
                R.drawable.logo_uncrack
            ),
        )
    }
}

