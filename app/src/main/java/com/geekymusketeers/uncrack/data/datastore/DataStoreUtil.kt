package com.geekymusketeers.uncrack.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class DataStoreUtil @Inject constructor(context: Context) {

    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val IS_DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val IS_SS_BLOCK_KEY = booleanPreferencesKey("ss_block")
    }
}