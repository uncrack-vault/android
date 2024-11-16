package com.aritradas.uncrack.presentation.vault.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.domain.repository.AccountRepository
import com.aritradas.uncrack.util.EncryptionUtils
import com.aritradas.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPasswordViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    var accountModel by mutableStateOf(
        Account(
            id = 0,
            company = "",
            email = "",
            category = "",
            username = "",
            password = "",
            note = "",
            dateTime = "",
            isFavourite = false
        )
    )

    var decryptedEmail by mutableStateOf("")
    var decryptedUsername by mutableStateOf("")
    var decryptedPassword by mutableStateOf("")

    fun getAccountById(accountId: Int) = runIO {
        repository.getAccountById(accountId).collect { response ->
            accountModel = response
            decryptData(response)
        }
    }

    private fun decryptData(account: Account) {
        try {
            decryptedEmail = EncryptionUtils.decrypt(account.email)
            decryptedUsername = EncryptionUtils.decrypt(account.username)
            decryptedPassword = EncryptionUtils.decrypt(account.password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateAccount(account: Account) = runIO {
        val encryptedUpdatedAccount = account.copy(
            email = EncryptionUtils.encrypt(account.email),
            username = if (account.username.isNotEmpty()) EncryptionUtils.encrypt(account.username) else "",
            password = EncryptionUtils.encrypt(account.password),
        )
        repository.editAccount(encryptedUpdatedAccount)
    }

    fun updateEmail(email: String) {
        decryptedEmail = email
    }

    fun updateUserName(userName: String) {
        decryptedUsername = userName
    }

    fun updatePassword(password: String) {
        decryptedPassword = password
    }

    fun updateNote(note: String) {
        accountModel = accountModel.copy(note = note)
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}