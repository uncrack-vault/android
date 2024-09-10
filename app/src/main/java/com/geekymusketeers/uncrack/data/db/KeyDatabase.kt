package com.geekymusketeers.uncrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geekymusketeers.uncrack.data.dao.KeyDao
import com.geekymusketeers.uncrack.domain.model.Key

@Database(
    entities = [Key::class],
    version = 3,
    exportSchema = false
)
abstract class KeyDatabase : RoomDatabase() {
    abstract val keyDao: KeyDao
}