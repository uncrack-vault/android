package com.geekymusketeers.uncrack.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.domain.repository.KeyRepository
import com.geekymusketeers.uncrack.data.db.KeyDatabase
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    private val keyRepository: KeyRepository

    private val _masterKeyLiveData = MutableLiveData<String>()
    val masterKeyLiveData: LiveData<String> = _masterKeyLiveData

    private val _confirmMasterKeyLiveData = MutableLiveData<String>()
    val confirmMasterKeyLiveData: LiveData<String> = _confirmMasterKeyLiveData

    private val _enableButtonLiveData = MutableLiveData<Boolean>()
    val enableButtonLiveData: LiveData<Boolean> = _enableButtonLiveData

    init {
        val keyDao = KeyDatabase.getDatabase(application).keyDao()
        keyRepository = KeyRepository(keyDao)
    }

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
        keyRepository.setMasterKey(key)
    }

    fun getMasterKey(): LiveData<List<Key>> = keyRepository.getMasterKey()
}