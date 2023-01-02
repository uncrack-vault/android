package com.geekymusketeers.uncrack.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.data.AccountDao
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.data.AccountDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository(private val accountDao: AccountDao) {
    val readAllAccount: LiveData<List<Account>> = accountDao.readAllAccount()

    suspend fun addAccount(account: Account){
        accountDao.addAccount(account)
    }
}