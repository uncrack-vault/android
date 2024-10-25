package com.aritradas.uncrack.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aritradas.uncrack.data.datastore.DataStoreUtil
import com.aritradas.uncrack.data.db.AccountDatabase
import com.aritradas.uncrack.data.db.KeyDatabase
import com.aritradas.uncrack.domain.repository.AccountRepository
import com.aritradas.uncrack.domain.repository.AccountRepositoryImpl
import com.aritradas.uncrack.domain.repository.KeyRepository
import com.aritradas.uncrack.domain.repository.KeyRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}