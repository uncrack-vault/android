package com.geekymusketeers.uncrack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekymusketeers.uncrack.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccount(account: Account)

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun readAllAccount(): LiveData<List<Account>>
}