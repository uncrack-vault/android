package com.aritradas.uncrack.sharedViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.MainActivity
import com.aritradas.uncrack.data.datastore.DataStoreUtil
import com.aritradas.uncrack.presentation.settings.BiometricAuthListener
import com.aritradas.uncrack.util.AppBioMetricManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val appBioMetricManager: AppBioMetricManager,
    dataStoreUtil: DataStoreUtil,
) : ViewModel() {

    private val dataStore = dataStoreUtil.dataStore

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _initAuth = MutableStateFlow(false)
    val initAuth: StateFlow<Boolean> = _initAuth.asStateFlow()

    private val _finishActivity = MutableStateFlow(false)
    val finishActivity: StateFlow<Boolean> = _finishActivity.asStateFlow()

    private val _updateDownloaded = MutableStateFlow(false)
    val updateDownloaded: StateFlow<Boolean> = _updateDownloaded.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                preferences[DataStoreUtil.IS_BIOMETRIC_AUTH_SET_KEY] ?: false
            }.collect { biometricAuthState ->
                if (biometricAuthState && appBioMetricManager.canAuthenticate()) {
                    _initAuth.emit(true)
                } else {
                    delay(1_000L)
                    _loading.emit(false)
                }
            }
        }
    }

    fun showBiometricPrompt(mainActivity: MainActivity) {
        appBioMetricManager.initBiometricPrompt(
            activity = mainActivity,
            listener = object : BiometricAuthListener {
                override fun onBiometricAuthSuccess() {
                    viewModelScope.launch {
                        _loading.emit(false)
                    }
                }

                override fun onUserCancelled() {
                    finishActivity()
                }

                override fun onErrorOccurred() {
                    finishActivity()
                }
            }
        )
    }

    private fun finishActivity() {
        viewModelScope.launch {
            _finishActivity.emit(true)
        }
    }

    fun notifyUpdateDownloaded() {
        viewModelScope.launch {
            _updateDownloaded.emit(true)
        }
    }
}