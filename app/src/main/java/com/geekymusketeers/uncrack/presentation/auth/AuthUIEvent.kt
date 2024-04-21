package com.geekymusketeers.uncrack.presentation.auth

sealed class AuthUIEvent {
    data class SignUpNameChanged(val value: String): AuthUIEvent()
    data class SignUpEmailChanged(val value: String): AuthUIEvent()
    data class SignUpPasswordChanged(val value: String): AuthUIEvent()
    object SignUp: AuthUIEvent()

    data class SignInNameChanged(val value: String): AuthUIEvent()
    data class SignInEmailChanged(val value: String): AuthUIEvent()
    data class SignInPasswordChanged(val value: String): AuthUIEvent()
    object SignIn: AuthUIEvent()
}