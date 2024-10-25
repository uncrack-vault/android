package com.aritradas.uncrack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aritradas.uncrack.data.dao.AccountDao
import com.aritradas.uncrack.domain.model.Account

@Database(
    entities = [Account::class],
    version = 4,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    abstract val accountDao : AccountDao
}