package com.geekymusketeers.uncrack.domain.repository

import com.geekymusketeers.uncrack.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun addAccount(account: Account)

    suspend fun editAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    fun getAllAccounts(): Flow<List<Account>>
}