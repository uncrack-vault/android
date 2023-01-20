package com.geekymusketeers.uncrack.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.data.AccountDao
import com.geekymusketeers.uncrack.model.Account


class AccountRepository(private val accountDao: AccountDao) {
    val readAllAccount: LiveData<List<Account>> = accountDao.readAllAccount()

    suspend fun addAccount(account: Account){
        accountDao.addAccount(account)
    }

    suspend fun editAccount(account: Account){
        accountDao.editAccount(account)
    }

    suspend fun deleteAccount(account: Account){
        accountDao.deleteAccount(account)
    }
}