package com.geekymusketeers.uncrack.presentation.masterKey

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.domain.repository.KeyRepository
import com.geekymusketeers.uncrack.util.aesDecrypt
import com.geekymusketeers.uncrack.util.runIO
import com.geekymusketeers.uncrack.util.toBase64String
import com.geekymusketeers.uncrack.util.toSecretKey
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Base64
import javax.crypto.SecretKey
import javax.inject.Inject

@HiltViewModel
class KeyViewModel @Inject constructor(
    private val repository: KeyRepository
) : ViewModel() {

    private val _keyModel = MutableStateFlow(Key(0,"",""))
    val keyModel  get() = _keyModel.asStateFlow()

    private val _masterKeyLiveData = MutableLiveData<String>()
    val masterKeyLiveData: LiveData<String> = _masterKeyLiveData

    private val _confirmMasterKeyLiveData = MutableLiveData<String>()
    val confirmMasterKeyLiveData: LiveData<String> = _confirmMasterKeyLiveData

    private val _enableButtonLiveData = MutableLiveData<Boolean>()
    val enableButtonLiveData: LiveData<Boolean> = _enableButtonLiveData

    private val _hasMinLength = MutableLiveData(false)
    val hasMinLength: LiveData<Boolean> = _hasMinLength

    private val _hasSymbol = MutableLiveData(false)
    val hasSymbol: LiveData<Boolean> = _hasSymbol

    private val _decryptedPassword = MutableLiveData<String>()
    val decryptedPassword: LiveData<String> = _decryptedPassword

    fun setMasterKey(masterKey: String) {
        _masterKeyLiveData.value = masterKey
        validatePassword(masterKey)
        checkMasterKey()
    }

    fun setConfirmMasterKey(confirmMasterKey: String) {
        _confirmMasterKeyLiveData.value = confirmMasterKey
        checkMasterKey()
    }

    fun checkMasterKey() {
        _enableButtonLiveData.value =
            _masterKeyLiveData.value.isNullOrBlank()
                .not() && _confirmMasterKeyLiveData.value.isNullOrBlank().not() &&
                    _masterKeyLiveData.value == _confirmMasterKeyLiveData.value
        isPasswordValid()
    }

    private fun validatePassword(password: String) {
        _hasMinLength.value = password.length >= 9
        _hasSymbol.value = password.any { !it.isLetterOrDigit() }
    }

    private fun isPasswordValid(): Boolean {
        return _hasMinLength.value == true && _hasSymbol.value == true
    }

    fun saveMasterKey(key: Key) = runIO {
        repository.setMasterKey(key)
    }

    fun getMasterKey() = runIO {
        repository.getMasterKey().collect { response ->
            android.util.Log.d("","Key is $response")
            _keyModel.value = response
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptPassword(encryptedPassword: ByteArray, key: SecretKey) {
        _decryptedPassword.value = aesDecrypt(encryptedPassword,key).toString()
    }
}