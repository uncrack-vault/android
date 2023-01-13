package com.geekymusketeers.uncrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.data.AccountDatabase
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Account>>
    private val repository : AccountRepository

    init {
        val accountDao = AccountDatabase.getDatabase(application).accountDao()
        repository = AccountRepository(accountDao)
        readAllData = repository.readAllAccount
    }

    fun addAccount(account: Account){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAccount(account)
        }
    }

    fun editAccount(account: Account){
        viewModelScope.launch(Dispatchers.IO){
            repository.editAccount(account)
        }
    }
}