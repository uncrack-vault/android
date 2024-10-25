package com.aritradas.uncrack.domain.repository

import com.aritradas.uncrack.domain.model.Key
import kotlinx.coroutines.flow.Flow

interface KeyRepository {

    suspend fun setMasterKey(key: Key)

    fun getMasterKey(): Flow<Key>

    suspend fun deleteMasterKey()
}