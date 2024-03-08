package com.geekymusketeers.uncrack.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.domain.repository.KeyRepository
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeyViewModel @Inject constructor(
    private val repository: KeyRepository
) : ViewModel() {

    var keyModel by mutableStateOf(emptyList<Key>())

    private val _masterKeyLiveData = MutableLiveData<String>()
    val masterKeyLiveData: LiveData<String> = _masterKeyLiveData

    private val _confirmMasterKeyLiveData = MutableLiveData<String>()
    val confirmMasterKeyLiveData: LiveData<String> = _confirmMasterKeyLiveData

    private val _enableButtonLiveData = MutableLiveData<Boolean>()
    val enableButtonLiveData: LiveData<Boolean> = _enableButtonLiveData

    fun setMasterKey(masterKey: String) {
        _masterKeyLiveData.value = masterKey
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