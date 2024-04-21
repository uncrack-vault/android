package com.geekymusketeers.uncrack.presentation.auth

sealed class AuthUIEvent {
    data class SignUpNameChanged(val value: String): AuthUIEvent()
    data class SignUpEmailChanged(val value: String): AuthUIEvent()
    data class SignUpPasswordChanged(val value: String): AuthUIEvent()
    object SignUp: AuthUIEvent()

    data class SignInEmail(val value: String): AuthUIEvent()
    data class SignInPassword(val value: String): AuthUIEvent()
    object SignIn: AuthUIEvent()
}