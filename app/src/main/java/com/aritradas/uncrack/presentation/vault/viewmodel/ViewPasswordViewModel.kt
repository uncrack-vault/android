package com.aritradas.uncrack.presentation.vault.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.domain.repository.AccountRepository
import com.aritradas.uncrack.util.EncryptionUtils
import com.aritradas.uncrack.util.runIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewPasswordViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    var accountModel: Account? by mutableStateOf(
        Account(
            id = 0,
            company = "",
            email = "",
            category = "",
            username = "",
            password = "",
            note = "",
            dateTime = "",
            isFavourite = false
        )
    )

    var decryptedEmail by mutableStateOf("")
    var decryptedUsername by mutableStateOf("")
    var decryptedPassword by mutableStateOf("")

    fun getAccountById(accountId: Int) = runIO {
        repository.getAccountById(accountId).collect { response ->
            accountModel = response
            decryptData(response)
        }
    }

    private fun decryptData(account: Account?) {
        try {
            account?.let {
                decryptedEmail = EncryptionUtils.decrypt(it.email)
                decryptedUsername = EncryptionUtils.decrypt(it.username)
                decryptedPassword = EncryptionUtils.decrypt(it.password)

                // Log if any decryption resulted in empty strings (potential decryption failure)
                if (it.email.isNotEmpty() && decryptedEmail.isEmpty()) {
                    Timber.w("Email decryption resulted in empty string")
                }
                if (it.username.isNotEmpty() && decryptedUsername.isEmpty()) {
                    Timber.w("Username decryption resulted in empty string")
                }
                if (it.password.isNotEmpty() && decryptedPassword.isEmpty()) {
                    Timber.w("Password decryption resulted in empty string")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to decrypt account data")
            // Reset to empty strings on decryption failure
            decryptedEmail = ""
            decryptedUsername = ""
            decryptedPassword = ""
        }
    }

    fun updateAccount(account: Account) = runIO {
        try {
            val encryptedUpdatedAccount = account.copy(
                email = EncryptionUtils.encrypt(account.email),
                username = if (account.username.isNotEmpty()) EncryptionUtils.encrypt(account.username) else "",
                password = EncryptionUtils.encrypt(account.password),
            )
            repository.editAccount(encryptedUpdatedAccount)
        } catch (e: Exception) {
            Timber.e(e, "Failed to encrypt and update account data")
            throw e // Re-throw to let the UI handle the error
        }
    }

    fun updateEmail(email: String) {
        decryptedEmail = email
    }

    fun updateUserName(userName: String) {
        decryptedUsername = userName
    }

    fun updatePassword(password: String) {
        decryptedPassword = password
    }

    fun updateNote(note: String) {
        accountModel?.let {
            accountModel = it.copy(note = note)
        }
    }

    fun deleteAccount(account: Account?) {
        viewModelScope.launch {
            account?.let {
                repository.deleteAccount(it)
                accountModel = Account(
                    id = 0,
                    company = "",
                    email = "",
                    category = "",
                    username = "",
                    password = "",
                    note = "",
                    dateTime = "",
                    isFavourite = false
                )
            }
        }
    }
}
