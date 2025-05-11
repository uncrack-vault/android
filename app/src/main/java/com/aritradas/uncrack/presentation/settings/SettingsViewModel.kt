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

    fun shouldLockApp(): Boolean {
        return _autoLockEnabled.value
    }

    fun shouldUseBiometric(): Boolean {
        return _biometricAuthState.value
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