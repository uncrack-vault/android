package com.geekymusketeers.uncrack.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.geekymusketeers.uncrack.data.datastore.DataStoreUtil
import com.geekymusketeers.uncrack.data.db.AccountDatabase
import com.geekymusketeers.uncrack.data.db.KeyDatabase
import com.geekymusketeers.uncrack.data.db.KeyDatabase_Impl
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.domain.repository.AccountRepositoryImpl
import com.geekymusketeers.uncrack.domain.repository.KeyRepository
import com.geekymusketeers.uncrack.domain.repository.KeyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context): DataStoreUtil =
        DataStoreUtil(context)

    @Provides
    @Singleton
    fun provideAccountDatabase(app: Application) : AccountDatabase {

        return Room.databaseBuilder(
            app,
            AccountDatabase::class.java,
            "account_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        db: AccountDatabase
    ): AccountRepository {
        return AccountRepositoryImpl(accountDao = db.accountDao)
    }

    @Provides
    @Singleton
    fun provideKeyDatabase(app: Application): KeyDatabase {

        return Room.databaseBuilder(
            app,
            KeyDatabase::class.java,
            "masterKey_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideKeyRepository(
        keyDB: KeyDatabase
    ): KeyRepository {
        return KeyRepositoryImpl(keyDao = keyDB.keyDao)
    }
}