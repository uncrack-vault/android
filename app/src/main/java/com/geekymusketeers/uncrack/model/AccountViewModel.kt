package com.geekymusketeers.uncrack.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.data.Account
import com.geekymusketeers.uncrack.repo.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository

) : ViewModel() {

    val accounts = accountRepository.allAccount()
    private val _searchAccount = MutableStateFlow<List<Account>>(emptyList())
    val searchAccount : StateFlow<List<Account>> = _searchAccount

    fun upsertAccount(account: Account){
        viewModelScope.launch {
            accountRepository.upsertNote(account)
        }
    }

}