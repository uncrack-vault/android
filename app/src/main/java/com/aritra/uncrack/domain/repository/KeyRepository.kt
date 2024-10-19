package com.aritra.uncrack.domain.repository

import com.aritra.uncrack.domain.model.Key
import kotlinx.coroutines.flow.Flow

interface KeyRepository {

    suspend fun setMasterKey(key: Key)

    fun getMasterKey(): Flow<Key>

    suspend fun deleteMasterKey()
}