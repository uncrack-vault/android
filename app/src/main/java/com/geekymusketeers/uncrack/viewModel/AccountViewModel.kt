package com.geekymusketeers.uncrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.data.room.AccountDatabase
import com.geekymusketeers.uncrack.data.model.Account
import com.geekymusketeers.uncrack.data.repository.AccountRepository
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
        viewModelScope.launch{
            repository.addAccount(account)
        }
    }

    fun editAccount(account: Account){
        viewModelScope.launch{
            repository.editAccount(account)
        }
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
    fun updateBookmark(account: Account) {
        viewModelScope.launch {
            repository.updateFavourite(account)
        }
    }
}