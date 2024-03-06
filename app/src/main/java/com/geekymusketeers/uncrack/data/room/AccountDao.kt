package com.geekymusketeers.uncrack.data.room

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

    @Update
    suspend fun editAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account_table ORDER BY id ASC")
    fun readAllAccount(): LiveData<List<Account>>

    // Favourite
    @Query("SELECT * FROM account_table WHERE isFavourite = 1")
    fun getFavourite(): LiveData<List<Account>>

    @Query("SELECT isFavourite from account_table where id = :id")
    suspend fun updateFavourite(id: Int): Boolean

    @Query("UPDATE account_table SET isFavourite = 1 WHERE id = :id")
    suspend fun isSelected(id: Int)

    @Query("UPDATE account_table SET isFavourite = 0 WHERE id = :id")
    suspend fun isNotSelected(id: Int)


}