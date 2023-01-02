package com.geekymusketeers.uncrack.di

import android.app.Application
import com.geekymusketeers.uncrack.data.AccountDao
import com.geekymusketeers.uncrack.data.AccountDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application : Application) : AccountDatabase{
        return AccountDatabase.getDatabase(application)
    }

    @Singleton
    @Provides
    fun provideDao(accountDatabase: AccountDatabase) : AccountDao {
        return accountDatabase.accountDao
    }
}