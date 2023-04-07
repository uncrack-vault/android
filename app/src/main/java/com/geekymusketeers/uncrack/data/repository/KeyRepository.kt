package com.geekymusketeers.uncrack.data.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.data.model.Key
import com.geekymusketeers.uncrack.data.room.KeyDao

class KeyRepository(private val keyDao: KeyDao) {

    fun getMasterKey(): LiveData<List<Key>> = keyDao.getMasterKey()

    suspend fun setMasterKey(key: Key) {
        keyDao.setMasterKey(key)
    }
}