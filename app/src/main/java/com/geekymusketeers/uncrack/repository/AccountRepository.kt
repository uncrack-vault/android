package com.geekymusketeers.uncrack.repository

import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.data.AccountDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    accountDatabase: AccountDatabase
) {

    private val accountDao = accountDatabase.accountDao

    suspend fun upsertNote(account: Account) = accountDao.upsertAccount(account)


    fun allAccount() : Flow<List<Account>> = accountDao.allAccount()

}