package com.geekymusketeers.uncrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geekymusketeers.uncrack.data.dao.CardDao
import com.geekymusketeers.uncrack.domain.model.Card

@Database(
    entities = [Card::class],
    version = 2
)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao() : CardDao

    companion object{
        @Volatile
        var INSTANCE : CardDatabase? = null
        @Synchronized
        fun getCardDatabase(context: Context) : CardDatabase {

            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "card_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}