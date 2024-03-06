package com.geekymusketeers.uncrack.domain.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.data.room.AccountDao
import com.geekymusketeers.uncrack.domain.model.Account


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

     fun getFavouriteAccount(){
        accountDao.getFavourite()
    }

    suspend fun updateFavourite(account: Account){

        val id  = account.id
        val test:Boolean = accountDao.updateFavourite(id)
        if (test) accountDao.isNotSelected(id)
        else accountDao.isSelected(id)

    }
}