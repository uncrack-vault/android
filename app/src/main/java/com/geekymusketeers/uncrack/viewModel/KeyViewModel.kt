package com.geekymusketeers.uncrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.data.model.Key
import com.geekymusketeers.uncrack.data.repository.KeyRepository
import com.geekymusketeers.uncrack.data.room.KeyDatabase
import kotlinx.coroutines.launch

class KeyViewModel(application: Application) : AndroidViewModel(application) {

    private val keyRepository: KeyRepository

    init {
        val keyDao = KeyDatabase.getDatabase(application).keyDao()
        keyRepository = KeyRepository(keyDao)
    }

    fun setMasterKey(key: Key){
        viewModelScope.launch {
            keyRepository.setMasterKey(key)
        }
    }
    fun getMasterKey() : LiveData<List<Key>> = keyRepository.getMasterKey()
}