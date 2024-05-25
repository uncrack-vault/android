package com.geekymusketeers.uncrack.presentation.tools.passwordHealth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassHealthViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {

    private val _strongPasswordCount = MutableLiveData<Int>()
    val strongPasswordCount: LiveData<Int> = _strongPasswordCount

    private val _weakPasswordCount = MutableLiveData<Int>()
    val weakPasswordCount: LiveData<Int> = _weakPasswordCount

    fun getPasswords() = runIO {

        var strongPasswordCount = 0
        var weakPasswordCount = 0

        Timber.d("Get all passwords")
        repository.getAllPasswords().collectLatest { passwords ->
            for (password in passwords) {
                val strength = getPasswordStrength(password)
                when(strength) {
                    PasswordStrength.STRONG -> strongPasswordCount++
                    PasswordStrength.WEAK -> weakPasswordCount++
                }
            }
            _strongPasswordCount.postValue(strongPasswordCount)
            _weakPasswordCount.postValue(weakPasswordCount)
        }
    }

    private fun getPasswordStrength(password: String): PasswordStrength {
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialCharacter = password.any { it !in ('a'..'z') && it !in ('A'..'Z') && it !in ('0'..'9') }

        return when {
            hasLowerCase && hasUpperCase && hasDigit && hasSpecialCharacter -> PasswordStrength.STRONG
            else -> PasswordStrength.WEAK
        }
    }
}

enum class PasswordStrength {
    STRONG,
    WEAK
}