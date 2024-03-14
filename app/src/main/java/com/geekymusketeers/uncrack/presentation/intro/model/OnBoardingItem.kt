package com.geekymusketeers.uncrack.presentation.intro.model

import android.content.Context
import com.geekymusketeers.uncrack.R

data class OnBoardingItem(
    val title: String,
    val text: String,
    val image: Int
) {
    companion object {
        // TODO: Need to fix the strings
        fun onboardingScreenItems(context: Context) = listOf(
            OnBoardingItem(
                "Heading",
                "Subtext",
                R.drawable.logo_uncrack
            ),
            OnBoardingItem(
                "Heading",
                "Subtext",
                R.drawable.logo_uncrack
            ),
            OnBoardingItem(
                "Heading",
                "Subtext",
                R.drawable.logo_uncrack
            ),
        )
    }
}

