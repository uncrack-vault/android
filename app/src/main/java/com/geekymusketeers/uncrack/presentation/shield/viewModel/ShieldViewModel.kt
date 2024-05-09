package com.geekymusketeers.uncrack.presentation.shield.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.repository.AccountRepository
import com.geekymusketeers.uncrack.util.UtilsKt.calculateAllPasswordsScore
import com.geekymusketeers.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShieldViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {

    private val _passwordStrengthScore = MutableLiveData<Int>()
    val passwordStrengthScore: LiveData<Int> = _passwordStrengthScore

    fun getPasswords() = runIO {

        var totalScore = 0

        Timber.d("Get all passwords")
        repository.getAllPasswords().collectLatest { passwords ->
            for (password in passwords) {
                Timber.d("Passwords are $password")
                val score = calculateAllPasswordsScore(password)
                Timber.d("Score of password $password and $score")
                totalScore += score
            }
            _passwordStrengthScore.postValue(totalScore)
        }
    }

    private fun passwordStrength(password: String): Int {
        return when {
            password.length >= 8 -> 10
            password.length >= 6 -> 7
            else -> 5
        }
    }
}