package com.geekymusketeers.uncrack.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val signUpName: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signInName: String = "",
    val signInEmail: String = "",
    val signInPassword: String = ""
)
