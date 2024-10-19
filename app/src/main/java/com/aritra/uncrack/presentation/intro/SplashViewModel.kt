package com.aritra.uncrack.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritra.uncrack.domain.repository.KeyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val keyRepository: KeyRepository
): ViewModel() {

    fun checkMasterKeyPresent(
        onMasterKeyExists: () -> Unit,
        onMasterKeyNotExists: () -> Unit
    ) {
        viewModelScope.launch {
            val key = keyRepository.getMasterKey().first()
            if (key != null) {
                onMasterKeyExists()
            } else {
                onMasterKeyNotExists()
            }
        }
    }
}