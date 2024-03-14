package com.geekymusketeers.uncrack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekymusketeers.uncrack.data.dao.AccountDao
import com.geekymusketeers.uncrack.domain.model.Account

@Database(
    entities = [Account::class],
    version = 4,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    abstract val accountDao : AccountDao
}