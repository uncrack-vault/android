package com.geekymusketeers.uncrack.domain.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.data.dao.AccountDao
import com.geekymusketeers.uncrack.domain.model.Account
import kotlinx.coroutines.flow.Flow


class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun addAccount(account: Account) {
        accountDao.addAccount(account)
    }

    override suspend fun editAccount(account: Account) {
        accountDao.editAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    override fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.readAllAccount()
    }

}