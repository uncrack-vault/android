package com.aritradas.uncrack.presentation.settings

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.MainActivity
import com.aritradas.uncrack.data.datastore.DataStoreUtil
import com.aritradas.uncrack.domain.repository.AccountRepository
import com.aritradas.uncrack.domain.repository.KeyRepository
import com.aritradas.uncrack.util.AppBioMetricManager
import com.aritradas.uncrack.util.runIO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val keyRepository: KeyRepository,
    private val accountRepository: AccountRepository,
    private val appBioMetricManager: AppBioMetricManager,
    dataStoreUtil: DataStoreUtil
): ViewModel() {

    private val auth = Firebase.auth
    val onLogOutComplete = MutableLiveData<Boolean>()
    val onDeleteAccountComplete = MutableLiveData<Boolean>()
    private val _isScreenshotEnabled = MutableLiveData(false)
    val isScreenshotEnabled: LiveData<Boolean> get() = _isScreenshotEnabled
    private val user = auth.currentUser
    private val userDB = FirebaseFirestore.getInstance()
    private val dataStore = dataStoreUtil.dataStore
    private val _biometricAuthState = MutableStateFlow(false)
    val biometricAuthState: StateFlow<Boolean> = _biometricAuthState
    private val _autoLockEnabled = MutableStateFlow(false)
    val autoLockEnabled: StateFlow<Boolean> = _autoLockEnabled

    // Available timeout options in milliseconds
    val autoLockTimeoutOptions = listOf(
        5000L,       // 5 seconds
        30000L,      // 30 seconds
        60000L,      // 1 minute
        180000L,     // 3 minutes
        300000L,     // 5 minutes
        900000L      // 15 minutes
    )

    // Human-readable timeout labels
    val autoLockTimeoutLabels = listOf(
        "5 seconds",
        "30 seconds",
        "1 minute",
        "3 minutes",
        "5 minutes",
        "15 minutes"
    )

    private val _autoLockTimeout = MutableStateFlow(60000L) // Default: 1 minute
    val autoLockTimeout: StateFlow<Long> = _autoLockTimeout

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                preferences[DataStoreUtil.IS_BIOMETRIC_AUTH_SET_KEY] ?: false
            }.collect {
                _biometricAuthState.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                preferences[DataStoreUtil.IS_AUTO_LOCK_ENABLED_KEY] ?: false
            }.collect {
                _autoLockEnabled.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                preferences[DataStoreUtil.AUTO_LOCK_TIMEOUT_KEY] ?: 60000L // Default: 1 minute
            }.collect {
                _autoLockTimeout.value = it
            }
        }
    }

    fun showBiometricPrompt(activity: MainActivity) {
        appBioMetricManager.initBiometricPrompt(
            activity = activity,
            listener = object : BiometricAuthListener {
                override fun onBiometricAuthSuccess() {
                    viewModelScope.launch {
                        dataStore.edit { preferences ->
                            preferences[DataStoreUtil.IS_BIOMETRIC_AUTH_SET_KEY] =
                                !_biometricAuthState.value
                        }
                    }
                }

                override fun onUserCancelled() {
                }

                override fun onErrorOccurred() {
                }
            }
        )
    }

    fun setScreenshotEnabled(enabled: Boolean) {
        _isScreenshotEnabled.value = enabled
    }

    fun setAutoLockEnabled(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DataStoreUtil.IS_AUTO_LOCK_ENABLED_KEY] = enabled
            }
        }
    }

    fun setAutoLockTimeout(timeoutMs: Long) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DataStoreUtil.AUTO_LOCK_TIMEOUT_KEY] = timeoutMs
            }
        }
    }

    fun getTimeoutLabelForValue(timeoutMs: Long): String {
        val index = autoLockTimeoutOptions.indexOf(timeoutMs)
        return if (index >= 0) autoLockTimeoutLabels[index] else "1 minute"
    }

    fun getSelectedTimeoutIndex(): Int {
        val timeout = _autoLockTimeout.value
        return autoLockTimeoutOptions.indexOf(timeout).takeIf { it >= 0 }
            ?: 2 // Default to 1 minute (index 2)
    }
    
    fun shouldLockApp(): Boolean {
        return _autoLockEnabled.value
    }

    fun shouldUseBiometric(): Boolean {
        return _biometricAuthState.value
    }

    fun getAutoLockTimeoutMs(): Long {
        return _autoLockTimeout.value
    }

    fun logout() = runIO {
        keyRepository.deleteMasterKey()
        accountRepository.deleteAccountDetails()
        FirebaseAuth.getInstance().signOut()
        onLogOutComplete.postValue(true)
    }

    fun deleteAccount() = runIO {
        user?.let {
            userDB.collection("Users").document(it.uid).delete()
        }
        onDeleteAccountComplete.postValue(true)
    }
}