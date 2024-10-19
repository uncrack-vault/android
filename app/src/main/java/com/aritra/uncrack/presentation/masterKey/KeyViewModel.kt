package com.aritra.uncrack.presentation.masterKey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.uncrack.domain.model.Key
import com.aritra.uncrack.domain.repository.KeyRepository
import com.aritra.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeyViewModel @Inject constructor(
    private val repository: KeyRepository
) : ViewModel() {

    var keyModel by mutableStateOf(Key(0,""))

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

    fun setMasterKey(masterKey: String) {
        _masterKeyLiveData.value = masterKey
        validatePassword(masterKey)
        checkMasterKey()
    }

    fun setConfirmMasterKey(confirmMasterKey: String) {
        _confirmMasterKeyLiveData.value = confirmMasterKey
        checkMasterKey()
    }

    private fun checkMasterKey() {
        _enableButtonLiveData.value = isPasswordValid() &&
                _masterKeyLiveData.value == _confirmMasterKeyLiveData.value
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
            keyModel = response
        }
    }
}