package com.geekymusketeers.uncrack.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.User
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    val resetPassword = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val registerStatus = MutableLiveData<Boolean>()

    fun resetPassword(email: String) = runIO {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnSuccessListener {
                resetPassword.postValue(true)
            }.addOnFailureListener {
                errorLiveData.postValue(it.message.toString())
            }
    }

    fun logIn(
        email: String,
        password: String,
        onSignedIn: (FirebaseUser) -> Unit,
    ) = runIO {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onSignedIn(user!!)
                } else {
                    errorLiveData.postValue("Please check your details")
                }
            }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        onSignedUp: (FirebaseUser) -> Unit,
    ) = runIO {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val userProfile = User(name, email, password)

                    val userDB = FirebaseFirestore.getInstance()
                    user?.let {
                        userDB.collection("Users")
                            .document(it.uid)
                            .set(userProfile)
                            .addOnSuccessListener {
                                onSignedUp(user)
                            }
                            .addOnFailureListener {
                                errorLiveData.postValue(it.message.toString())
                            }
                    }
                } else {
                    errorLiveData.postValue("Please check your details")
                }
            }
    }
}