package com.aritra.uncrack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aritra.uncrack.data.dao.KeyDao
import com.aritra.uncrack.domain.model.Key

@Database(
    entities = [Key::class],
    version = 3,
    exportSchema = false
)
abstract class KeyDatabase : RoomDatabase() {
    abstract val keyDao: KeyDao
}