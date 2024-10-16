package com.geekymusketeers.uncrack.domain.repository

import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.data.dao.KeyDao
import kotlinx.coroutines.flow.Flow

class KeyRepositoryImpl(
    private val keyDao: KeyDao
): KeyRepository {

    override fun getMasterKey(): Flow<Key> {
        return keyDao.getMasterKey()
    }

    override suspend fun deleteMasterKey() {
        keyDao.deleteMasterKey()
    }

    override suspend fun setMasterKey(key: Key) {
        keyDao.setMasterKey(key)
    }
}