package com.aritradas.uncrack.sharedViewModel

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.data.datastore.DataStoreUtil
import com.aritradas.uncrack.data.datastore.DataStoreUtil.Companion.IS_SS_BLOCK_KEY
import com.aritradas.uncrack.data.datastore.DataStoreUtil.Companion.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ThemeState(val themeMode: ThemeMode, val isSsBlocked: Boolean)
enum class ThemeMode { LIGHT, DARK, SYSTEM }

@HiltViewModel
class ThemeViewModel @Inject constructor(
    dataStoreUtil: DataStoreUtil
): ViewModel() {

    private val _themeState =
        MutableStateFlow(ThemeState(themeMode = ThemeMode.SYSTEM, isSsBlocked = false))
    val themeState: StateFlow<ThemeState> = _themeState

    private val dataStore = dataStoreUtil.dataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(
                    themeMode = ThemeMode.valueOf(
                        preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
                    ),
                    isSsBlocked = preferences[IS_SS_BLOCK_KEY] ?: false
                )
            }.collect {
                _themeState.value = it
            }
        }
    }

    fun toggleTheme() {
        val nextMode = when (_themeState.value.themeMode) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.SYSTEM
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[THEME_MODE_KEY] = nextMode.name
            }
        }
    }
}