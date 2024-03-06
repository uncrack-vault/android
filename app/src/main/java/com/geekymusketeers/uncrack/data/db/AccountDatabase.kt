package com.geekymusketeers.uncrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geekymusketeers.uncrack.data.room.AccountDao
import com.geekymusketeers.uncrack.domain.model.Account

@Database(
    entities = [Account::class],
    version = 4
)
abstract class AccountDatabase : RoomDatabase(){

    abstract fun accountDao() : AccountDao

    companion object{
        @Volatile
        var INSTANCE : AccountDatabase? = null

        @Synchronized
        fun getDatabase(context:Context)  : AccountDatabase {

            val tempInstance = INSTANCE

            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDatabase::class.java,
                    "account_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}