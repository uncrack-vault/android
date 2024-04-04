package com.geekymusketeers.uncrack.presentation.vault.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
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
}