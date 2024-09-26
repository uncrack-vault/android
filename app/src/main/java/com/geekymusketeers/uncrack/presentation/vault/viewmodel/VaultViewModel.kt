package com.geekymusketeers.uncrack.presentation.vault.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {


    private val _accountModel = MutableStateFlow<List<Account>>(emptyList())
    val accountModel: StateFlow<List<Account>> = _accountModel

    private val _filteredAccounts = MutableStateFlow<List<Account>>(emptyList())
    val filteredAccounts: StateFlow<List<Account>> = _filteredAccounts

    fun getAccounts() = runIO {
        repository.getAllAccounts().collectLatest { response ->
            _accountModel.value = response
            _filteredAccounts.value = response
        }
    }

    fun searchAccount(query: String) {
        _filteredAccounts.update {
            if (query.isEmpty()) {
                _accountModel.value
            } else {
                _accountModel.value.filter { account ->
                    account.company.contains(query, true)
                }
            }
        }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch{
            repository.addAccount(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}