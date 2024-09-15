package com.geekymusketeers.uncrack.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.util.BiometricPreference
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val preference: BiometricPreference
): ViewModel() {

    private val auth = Firebase.auth
    val isBiometricEnabled = MutableStateFlow(false)
    val onLogOutComplete = MutableLiveData<Boolean>()
    val onDeleteAccountComplete = MutableLiveData<Boolean>()
    private val _isScreenshotEnabled = MutableLiveData(false)
    val isScreenshotEnabled: LiveData<Boolean> get() = _isScreenshotEnabled
    private val user = auth.currentUser
    private val userDB = FirebaseFirestore.getInstance()

    init {
        viewModelScope.launch {
            isBiometricEnabled.value = preference.isBiometricEnabled()
        }
    }

    fun setScreenshotEnabled(enabled: Boolean) {
        _isScreenshotEnabled.value = enabled
    }

    fun logout() = runIO {
        FirebaseAuth.getInstance().signOut()
        onLogOutComplete.postValue(true)
    }

    fun deleteAccount() = runIO {
        user?.let {
            userDB.collection("Users").document(it.uid).delete()
        }
        onDeleteAccountComplete.postValue(true)
    }

    fun setBiometricEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            preference.setBiometricEnabled(isEnabled)
            isBiometricEnabled.value = isEnabled
        }
    }
}