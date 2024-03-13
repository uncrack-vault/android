package com.geekymusketeers.uncrack.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.util.Constants.DEFAULT_PASSWORD_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordGeneratorViewModel @Inject constructor() : ViewModel() {

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordLength = MutableLiveData(DEFAULT_PASSWORD_LENGTH)
    val passwordLength: LiveData<Float> = _passwordLength

    private val _includeUppercase = MutableLiveData(true)
    val includeUppercase: LiveData<Boolean> = _includeUppercase

    private val _includeLowercase = MutableLiveData(true)
    val includeLowercase: LiveData<Boolean> = _includeLowercase

    private val _includeNumbers = MutableLiveData(true)
    val includeNumbers: LiveData<Boolean> = _includeNumbers

    private val _includeSpecialChars = MutableLiveData(true)
    val includeSpecialChars: LiveData<Boolean> = _includeSpecialChars

    fun generatePassword() {
        _password.value = generatePassword(
            length = _passwordLength.value ?: DEFAULT_PASSWORD_LENGTH,
            includeUppercase = _includeUppercase.value ?: true,
            includeLowercase = _includeLowercase.value ?: true,
            includeNumbers = _includeNumbers.value ?: true,
            includeSpecialChars = _includeSpecialChars.value ?: true
        )
    }

    fun updatePasswordLength(newLength: Float) {
        _passwordLength.value = newLength
    }

    fun updateIncludeUppercase(include: Boolean) {
        _includeUppercase.value = include
    }

    fun updateIncludeLowercase(include: Boolean) {
        _includeLowercase.value = include
    }

    fun updateIncludeNumbers(include: Boolean) {
        _includeNumbers.value = include
    }

    fun updateIncludeSpecialChars(include: Boolean) {
        _includeSpecialChars.value = include
    }

    private fun generatePassword(
        length: Float,
        includeUppercase: Boolean,
        includeLowercase: Boolean,
        includeNumbers: Boolean,
        includeSpecialChars: Boolean
    ): String {
        val charPool = buildString {
            if (includeUppercase) {
                append("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
            }
            if (includeLowercase) {
                append("abcdefghijklmnopqrstuvwxyz")
            }
            if (includeNumbers) {
                append("0123456789")
            }
            if (includeSpecialChars) {
                append("!@#$%^&*()_+")
            }
        }

        return (1..length.toInt()).map { charPool.random() }.joinToString("")
    }
}
