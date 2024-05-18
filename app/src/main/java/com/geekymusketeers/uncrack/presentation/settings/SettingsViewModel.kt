package com.geekymusketeers.uncrack.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SettingsViewModel @Inject constructor(): ViewModel() {

    private val auth = Firebase.auth
    val onLogOutComplete = MutableLiveData<Boolean>()
    val onDeleteAccountComplete = MutableLiveData<Boolean>()
    private val _isScreenshotEnabled = MutableLiveData(false)
    val isScreenshotEnabled: LiveData<Boolean> get() = _isScreenshotEnabled
    private val user = auth.currentUser
    private val userDB = FirebaseFirestore.getInstance()

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
}