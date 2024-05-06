package com.geekymusketeers.uncrack.presentation.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekymusketeers.uncrack.domain.model.User
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    val state = mutableStateOf(User())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            try {
                val user = fetchDataFromFirestore()
                state.value = user
            } catch (e: Exception) {
                Timber.e("Error fetching data: $e")
            }
        }
    }

    private suspend fun fetchDataFromFirestore(): User {
        val db = FirebaseFirestore.getInstance()
        val userEntity = User()

        try {
            val querySnapshot = db.collection("Users").get().await()
            if (!querySnapshot.isEmpty) {
                val result = querySnapshot.toObjects(User::class.java)
                return result.first()
            }
        } catch (e: FirebaseFirestoreException) {
            Timber.e("getDataFromFirestore: $e")
        }

        return userEntity
    }
}
