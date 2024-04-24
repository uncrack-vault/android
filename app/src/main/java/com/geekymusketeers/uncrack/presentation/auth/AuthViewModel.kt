package com.geekymusketeers.uncrack.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.data.remote.repository.AuthRepository
import com.geekymusketeers.uncrack.data.remote.request.AuthResult
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthUIEvent) {
        when(event) {
            is AuthUIEvent.SignInNameChanged -> {
                state = state.copy(signInName = event.value)
            }
            is AuthUIEvent.SignInEmailChanged -> {
                state = state.copy(signInEmail = event.value)
            }
            is AuthUIEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUIEvent.SignIn -> {
                signIn()
            }
            is AuthUIEvent.SignUpNameChanged -> {
                state = state.copy(signUpName = event.value)
            }
            is AuthUIEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }
            is AuthUIEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUIEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() = runIO {
        state = state.copy(isLoading = true)
        val result = repository.signIn(
            name = state.signUpName,
            email = state.signUpEmail,
            password = state.signUpPassword
        )
        resultChannel.send(result)
        state = state.copy(isLoading = false)
    }

    private fun signIn() = runIO {
        state = state.copy(isLoading = true)
        val result = repository.signIn(
            name = state.signInName,
            email = state.signInEmail,
            password = state.signInPassword
        )
        resultChannel.send(result)
        state = state.copy(isLoading = false)
    }

    private fun authenticate() = runIO {
        state = state.copy(isLoading = true)
        val result = repository.authenticate()
        resultChannel.send(result)
        state = state.copy(isLoading = false)
    }
}