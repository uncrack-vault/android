package com.geekymusketeers.uncrack.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAccount(account: Account)

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun allAccount(): Flow<List<Account>>
}