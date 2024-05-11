package com.geekymusketeers.uncrack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.geekymusketeers.uncrack.domain.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccount(account: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account_table ORDER BY id ASC")
    fun readAllAccount(): Flow<List<Account>>

    @Query("SELECT * FROM account_table WHERE id = :accountId")
    fun getAccountById(accountId: Int) : Flow<Account>

    @Query("SELECT password FROM account_table")
    fun getAllPasswords(): Flow<List<String>>

    @Query("SELECT * FROM account_table WHERE password = :password")
    fun getReusedPasswordCount(password: String): Int

    @Query("SELECT * FROM account_table WHERE password = :password AND dateTime < :thresholdDate")
    fun getOldPasswordCount(password: String, thresholdDate: String): Int
}