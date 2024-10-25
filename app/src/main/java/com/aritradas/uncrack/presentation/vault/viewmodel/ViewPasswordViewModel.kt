package com.aritradas.uncrack.presentation.vault.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.domain.repository.AccountRepository
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

    fun getAccountById(accountId: Int) = runIO {
        repository.getAccountById(accountId).collect { response ->
            accountModel = response
        }
    }

    fun updateAccount(account: Account) = runIO {
        repository.editAccount(account)
    }

    fun updateEmail(email: String) {
        accountModel = accountModel.copy(email = email)
    }

    fun updateUserName(userName: String) {
        accountModel = accountModel.copy(username = userName)
    }

    fun updatePassword(password: String) {
        accountModel = accountModel.copy(password = password)
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