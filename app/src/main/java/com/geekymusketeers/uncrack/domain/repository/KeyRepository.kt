package com.geekymusketeers.uncrack.domain.repository

import com.geekymusketeers.uncrack.domain.model.Key
import kotlinx.coroutines.flow.Flow

interface KeyRepository {

    suspend fun setMasterKey(key: Key)

    fun getMasterKey(): Flow<List<Key>>
}