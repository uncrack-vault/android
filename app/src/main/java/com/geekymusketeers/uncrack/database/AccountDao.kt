package com.geekymusketeers.uncrack.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekymusketeers.uncrack.data.Account
import kotlinx.coroutines.flow.Flow

interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAccount(account: Account)

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun allAccount(): Flow<List<Account>>
}