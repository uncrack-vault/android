package com.geekymusketeers.uncrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.data.model.Key
import com.geekymusketeers.uncrack.data.repository.KeyRepository
import com.geekymusketeers.uncrack.data.room.KeyDatabase
import kotlinx.coroutines.launch

class KeyViewModel(application: Application) : AndroidViewModel(application) {

    private val keyRepository: KeyRepository
    private val masterKeyLiveData = MutableLiveData<String>()
    private val confirmMasterKeyLiveData = MutableLiveData<String>()
    val enableButtonLiveData = MutableLiveData<Boolean>()

    init {
        val keyDao = KeyDatabase.getDatabase(application).keyDao()
        keyRepository = KeyRepository(keyDao)
    }

    fun setMasterKey(masterKey: String) {
        masterKeyLiveData.value = masterKey
        checkMasterKey()
    }

    fun setConfirmMasterKey(confirmMasterKey: String) {
        confirmMasterKeyLiveData.value = confirmMasterKey
        checkMasterKey()
    }
    fun checkMasterKey() {
        enableButtonLiveData.value = !masterKeyLiveData.value.isNullOrBlank() && !confirmMasterKeyLiveData.value.isNullOrBlank() &&
                masterKeyLiveData.value == confirmMasterKeyLiveData.value
    }
    fun saveMasterKey(key: Key){
        viewModelScope.launch {
            keyRepository.setMasterKey(key)
        }
    }
    fun getMasterKey() : LiveData<List<Key>> = keyRepository.getMasterKey()
}