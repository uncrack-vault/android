package com.geekymusketeers.uncrack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.model.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditViewModel : ViewModel() {

    val saveStatus = MutableLiveData<Int>()
    val deleteStatus = MutableLiveData<Int>()
    val updateStatus = MutableLiveData<Int>()

    init {
        saveStatus.value = 0
        deleteStatus.value = 0
        updateStatus.value = 0
    }

    suspend fun saveData(
        accountsViewModel: AccountViewModel,
        account : Account
    ) = viewModelScope.launch {


        val createNewData = launch {


            val createAccountObjectInRoomDB = viewModelScope.async(Dispatchers.IO) {
                saveInRoomDB(accountsViewModel, account)
            }

            saveStatus.value =
                createAccountObjectInRoomDB.await()

        }

        createNewData.join()
        saveStatus.value = saveStatus.value?.plus(1)


    }

    private suspend fun saveInRoomDB(
        accountsViewModel: AccountViewModel,
        account: Account) : Int {

        return withContext(Dispatchers.IO){
            accountsViewModel.addAccount(account)
            1
        }

    }
}