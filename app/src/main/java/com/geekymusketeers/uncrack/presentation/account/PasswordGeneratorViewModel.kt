package com.geekymusketeers.uncrack.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PasswordGeneratorViewModel : ViewModel() {
    
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordLength = MutableLiveData<Int>()
    val passwordLength: LiveData<Int> = _passwordLength

    private val _includeUppercase = MutableLiveData<Boolean>()
    val includeUppercase: LiveData<Boolean> = _includeUppercase

    private val _includeLowercase = MutableLiveData<Boolean>()
    val includeLowercase: LiveData<Boolean> = _includeLowercase

    private val _includeNumbers = MutableLiveData<Boolean>()
    val includeNumbers: LiveData<Boolean> = _includeNumbers

    private val _includeSpecialChars = MutableLiveData<Boolean>()
    val includeSpecialChars: LiveData<Boolean> = _includeSpecialChars

    fun generatePassword() {
        _password.value = generatePassword(
            _passwordLength.value,
            _includeUppercase.value,
            _includeLowercase.value,
            _includeNumbers.value,
            _includeSpecialChars.value
        )
    }

    fun updatePasswordLength(newLength: Int) {
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
        length: Int?,
        includeUppercase: Boolean?,
        includeLowercase: Boolean?,
        includeNumbers: Boolean?,
        includeSpecialChars: Boolean?
    ): String {
        val charPool = buildString {
            if (includeUppercase == true) {
                append("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
            }
            if (includeLowercase == true) {
                append("abcdefghijklmnopqrstuvwxyz")
            }
            if (includeNumbers == true) {
                append("0123456789")
            }
            if (includeSpecialChars == true) {
                append("!@#$%^&*()_+")
            }
        }

        return (1..length!!)
            .map { charPool.random() }
            .joinToString("")
    }
}