package com.geekymusketeers.uncrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekymusketeers.uncrack.repository.AccountRepository
import javax.inject.Inject

class AccountViewModelProvider @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(accountRepository) as T
    }
}