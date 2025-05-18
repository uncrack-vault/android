package com.aritradas.uncrack.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class DataStoreUtil @Inject constructor(context: Context) {

    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val IS_DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val IS_SS_BLOCK_KEY = booleanPreferencesKey("ss_block")
        val IS_BIOMETRIC_AUTH_SET_KEY = booleanPreferencesKey("biometric_auth")
        val IS_AUTO_LOCK_ENABLED_KEY = booleanPreferencesKey("auto_lock_enabled")
        val AUTO_LOCK_TIMEOUT_KEY = longPreferencesKey("auto_lock_timeout")
    }
}