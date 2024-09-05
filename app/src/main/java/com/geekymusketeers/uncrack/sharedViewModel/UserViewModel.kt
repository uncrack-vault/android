package com.geekymusketeers.uncrack.sharedViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.domain.model.User
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    val state = mutableStateOf(User())

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = runIO {
            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val user = fetchUserFromFirestore(currentUser.uid)
                    state.value = user
                } else {
                    Timber.e("No user is currently logged in")
                }
            } catch (e: Exception) {
                Timber.e("Error fetching user data: $e")
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
}