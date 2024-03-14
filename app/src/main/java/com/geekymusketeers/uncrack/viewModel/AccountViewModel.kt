package com.geekymusketeers.uncrack.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {


    var accountModel by mutableStateOf(emptyList<Account>())

    fun getAccounts() = runIO {
        repository.getAllAccounts().collect { response ->
            accountModel = response
        }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch{
            repository.addAccount(account)
        }
    }

    fun editAccount(account: Account) {
        viewModelScope.launch{
            repository.editAccount(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}