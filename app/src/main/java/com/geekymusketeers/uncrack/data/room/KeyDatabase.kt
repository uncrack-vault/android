package com.geekymusketeers.uncrack.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class KeyDatabase : RoomDatabase() {

    abstract fun keyDao(): KeyDao

    companion object {
        @Volatile
        var INSTANCE : KeyDatabase? = null

        @Synchronized
        fun getDatabase(context: Context) : KeyDatabase {

            val tempInstance = INSTANCE

            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder (
                    context.applicationContext,
                    KeyDatabase::class.java,
                    "masterKey_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}