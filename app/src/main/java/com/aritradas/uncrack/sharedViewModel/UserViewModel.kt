package com.aritradas.uncrack.sharedViewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritradas.uncrack.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _state = MutableStateFlow(User())
    val state: StateFlow<User> = _state

    init {
        loadUserFromDataStore()
        getCurrentUser()
    }

    private fun loadUserFromDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userNamePreference = dataStore.data.first()
                val userName = userNamePreference[USER_NAME_KEY] ?: ""
                _state.value = _state.value.copy(name = userName)
            } catch (e: Exception) {
                Timber.e("Error loading user name from DataStore: $e")
            }
        }
    }

    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val user = fetchUserFromFirestore(currentUser.uid)
                _state.value = user
                saveUserNameToDataStore(user.name)
            } else {
                Timber.e("No user is currently logged in")
            }
        } catch (e: Exception) {
            Timber.e("Error fetching user data: $e")
        }
    }

    // Function to update the username in Firestore
    fun updateUserName(newName: String) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Users")
                    .document(currentUser.uid)
                    .update("name", newName)
                    .await()

                // Update local state and DataStore
                _state.value = _state.value.copy(name = newName)
                saveUserNameToDataStore(newName)
            } catch (e: Exception) {
                Timber.e("Error updating user name in Firestore: $e")
            }
        }
    }

    private suspend fun fetchUserFromFirestore(userId: String): User {
        return try {
            val documentSnapshot = firestore.collection("Users").document(userId).get().await()
            Timber.d("User id is; $userId")
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(User::class.java) ?: User()
            } else {
                Timber.e("User document does not exist for ID: $userId")
                User()
            }
        } catch (e: FirebaseFirestoreException) {
            Timber.e("Error fetching user from Firestore: $e")
            User()
        }
    }

    private suspend fun saveUserNameToDataStore(userName: String) {
        try {
            dataStore.edit { preferences ->
                preferences[USER_NAME_KEY] = userName
            }
        } catch (e: Exception) {
            Timber.e("Error saving user name to DataStore: $e")
        }
    }

    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }
}
