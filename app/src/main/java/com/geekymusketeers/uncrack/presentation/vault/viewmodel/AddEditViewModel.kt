package com.geekymusketeers.uncrack.presentation.vault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.UtilsKt.validateEmail
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isAdded = MutableLiveData(false)
    val isAdded: LiveData<Boolean> = _isAdded

    fun setEmail(email: String) {
        _email.value = email
        checkIfAdded()
    }

    fun setUserName(userName: String) {
        _username.value = userName
        checkIfAdded()
    }

    fun setPassword(password: String) {
        _password.value = password
        checkIfAdded()
    }

    fun addAccount(account: Account) = runIO {
        repository.addAccount(account)
    }

    private fun checkIfAdded() {
        val isEmailValid = validateEmail(email.value?.trim())
        val isAnyFieldNullOrEmpty =
            email.value.isNullOrEmpty()
                    || username.value.isNullOrEmpty()
                    || password.value.isNullOrEmpty()
                    || !isEmailValid

        _isAdded.value = isAnyFieldNullOrEmpty.not()
    }
}