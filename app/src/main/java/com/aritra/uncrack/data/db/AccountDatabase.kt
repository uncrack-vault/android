package com.aritra.uncrack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aritra.uncrack.data.dao.AccountDao
import com.aritra.uncrack.domain.model.Account

@Database(
    entities = [Account::class],
    version = 4,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    abstract val accountDao : AccountDao
}