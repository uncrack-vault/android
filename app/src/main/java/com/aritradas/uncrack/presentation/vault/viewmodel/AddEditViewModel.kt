package com.aritradas.uncrack.presentation.vault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.domain.repository.AccountRepository
import com.aritradas.uncrack.util.UtilsKt.validateEmail
import com.aritradas.uncrack.util.runIO
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

    private val _note = MutableLiveData<String>()
    val note: LiveData<String> = _note

    private val _isAdded = MutableLiveData(false)
    val isAdded: LiveData<Boolean> = _isAdded

    init {
        resetState()
    }

    fun resetState() {
        _email.value = ""
        _username.value = ""
        _password.value = ""
        _note.value = ""
        _isAdded.value = false
    }

    fun setEmail(email: String) {
        _email.value = email
        checkIfAdded()
    }

    fun setUserName(userName: String) {
        _username.value = userName
    }

    fun setPassword(password: String) {
        _password.value = password
        checkIfAdded()
    }

    fun setNote(note: String) {
        _note.value = note
    }

    fun addAccount(account: Account) = runIO {
        repository.addAccount(account)
    }

    private fun checkIfAdded() {
        val isEmailValid = validateEmail(email.value)
        val areFieldsFilled = !email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()

        _isAdded.value = areFieldsFilled && isEmailValid
    }
}