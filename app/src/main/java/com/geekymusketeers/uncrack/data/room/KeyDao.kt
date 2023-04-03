package com.geekymusketeers.uncrack.data.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekymusketeers.uncrack.data.model.Key

interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setMasterKey(key: Key)

    @Query("SELECT * FROM master_key")
    fun getMasterKey(): LiveData<List<Key>>
}