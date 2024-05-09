package com.geekymusketeers.uncrack.presentation.shield

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.UtilsKt.calculateAllPasswordsScore
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShieldViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {

    private val _passwordStrengthScore = MutableLiveData<Int>()
    val passwordStrengthScore: LiveData<Int> = _passwordStrengthScore

    fun getPasswords() = runIO {

        var totalScore = 0

        repository.getAllPasswords().collect { passwords ->
            for (password in passwords) {
                val score = passwordStrength(password)
                totalScore += score
            }
            _passwordStrengthScore.postValue(totalScore)
        }
    }

    private fun passwordStrength(password: String): Int {
        return when {
            password.length >= 8 -> 100
            password.length >= 6 -> 70
            else -> 50
        }
    }
}