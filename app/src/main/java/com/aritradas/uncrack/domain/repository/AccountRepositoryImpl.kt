package com.aritradas.uncrack.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.aritradas.uncrack.data.dao.AccountDao
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.util.UtilsKt.calculateThresholdDate
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

    override fun getAccountById(accountId: Int): Flow<Account> {
        return accountDao.getAccountById(accountId)
    }

    override fun getAllPasswords(): Flow<List<String>> {
        return accountDao.getAllPasswords()
    }

    override fun getReusedPasswordCount(password: String): Boolean {
        val count = accountDao.getReusedPasswordCount(password)
        return count > 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getOldPasswordCount(password: String): Boolean {
        val thresholdDate = calculateThresholdDate()
        val count = accountDao.getOldPasswordCount(password, thresholdDate)
        return count > 0
    }

    override suspend fun deleteAccountDetails() {
        accountDao.deleteAccountDetails()
    }
}