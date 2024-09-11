package com.geekymusketeers.uncrack.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.geekymusketeers.uncrack.util.Constants.PREF_BIOMETRIC
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Named

class BiometricPreference @Inject constructor(
    @Named(PREF_BIOMETRIC) private val preferenceDataStore: DataStore<Preferences>
) {
    object PreferencesKey {
        val KEY_BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
    }

    suspend fun isBiometricEnabled(): Boolean {
        return preferenceDataStore.data.first()[PreferencesKey.KEY_BIOMETRIC_ENABLED] ?: false
    }

    suspend fun setBiometricEnabled(isEnabled: Boolean) {
        preferenceDataStore.edit { preferences ->
            preferences[PreferencesKey.KEY_BIOMETRIC_ENABLED] = isEnabled
        }
    }
}