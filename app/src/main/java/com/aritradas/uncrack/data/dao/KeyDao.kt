package com.aritradas.uncrack.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aritradas.uncrack.domain.model.Key
import kotlinx.coroutines.flow.Flow

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMasterKey(key: Key)

    @Query("SELECT * FROM master_key")
    fun getMasterKey(): Flow<Key>

    @Query("DELETE FROM master_key")
    suspend fun deleteMasterKey()
}