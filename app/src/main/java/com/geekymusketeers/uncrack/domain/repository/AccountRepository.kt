package com.geekymusketeers.uncrack.domain.repository

import com.geekymusketeers.uncrack.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun addAccount(account: Account)

    suspend fun editAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    fun getAllAccounts(): Flow<List<Account>>

    fun getAccountById(accountId: Int) : Flow<Account>

    fun getAllPasswords(): Flow<List<String>>

    fun getReusedPasswordCount(password: String): Boolean

    fun getOldPasswordCount(password: String): Boolean

    suspend fun deleteAccountDetails()
}