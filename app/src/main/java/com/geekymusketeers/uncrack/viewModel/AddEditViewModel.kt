package com.geekymusketeers.uncrack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.VaultViewModel
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

    // Save Data

    suspend fun saveData(
        accountsViewModel: VaultViewModel,
        account : Account
    ) = viewModelScope.launch {


        val createNewData = launch {


            val createAccountObjectInRoomDB = viewModelScope.async(Dispatchers.IO) {
                saveInRoomDB(accountsViewModel, account)
            }

            saveStatus.postValue(createAccountObjectInRoomDB.await())

        }

        createNewData.join()
        saveStatus.value = saveStatus.value?.plus(1)


    }

    private suspend fun saveInRoomDB(
        accountsViewModel: VaultViewModel,
        account: Account
    ) : Int {

        return withContext(Dispatchers.IO){
            accountsViewModel.addAccount(account)
            1
        }
    }

    // Update Data

    suspend fun updateData(
        accountsViewModel: VaultViewModel,
        account: Account
    ) = viewModelScope.launch {

        val updateAccountObjectInRoomDB = viewModelScope.async(Dispatchers.IO) {
            updateInRoomDB(accountsViewModel, account)
        }

        updateStatus.value = updateStatus.value?.plus(updateAccountObjectInRoomDB.await())
    }

    private suspend fun updateInRoomDB(
        accountsViewModel: VaultViewModel,
        account: Account
    ): Int {

        return withContext(Dispatchers.IO) {
            try {
                accountsViewModel.editAccount(account)
//                Util.log("Updated in RoomDB")
                return@withContext 1
            } catch (e: java.lang.Exception) {
                Util.log("Error: $e")
                return@withContext 5
            }
        }
    }

    // Delete Data

    suspend fun deleteEntry(
        accountsViewModel: VaultViewModel,
        account: Account
    ) = viewModelScope.launch {

        val deleteData = launch {


            val deleteAccountObjectInRoomDB = viewModelScope.async(Dispatchers.IO) {
                deleteInRoomDB(accountsViewModel, account)
            }

            deleteStatus.value = (deleteAccountObjectInRoomDB.await())
        }

        deleteData.join()
        deleteStatus.value = deleteStatus.value?.plus(1)

    }

    private suspend fun deleteInRoomDB(
        accountsViewModel: VaultViewModel,
        account: Account
    ): Int {

        return withContext(Dispatchers.IO) {
            try {
                accountsViewModel.deleteAccount(account)
                Util.log("Deleted from RoomDB")
                1
            } catch (e: java.lang.Exception) {
                Util.log("Error: $e")
                5
            }

        }
    }
}