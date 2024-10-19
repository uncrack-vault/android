package com.aritra.uncrack.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.uncrack.domain.model.User
import com.aritra.uncrack.util.runIO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    val resetPassword = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val registerStatus = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    fun resetPassword(email: String) = runIO {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnSuccessListener {
                resetPassword.postValue(true)
            }.addOnFailureListener {
                errorLiveData.postValue(it.message.toString())
            }
    }

    fun logIn(email: String, password: String) = runIO {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess.postValue(true)
                } else {
                    errorLiveData.postValue("Invalid email or password")
                    loginSuccess.postValue(false)
                }
            }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        onSignedUp: (FirebaseUser) -> Unit,
    ) = runIO {
        auth.createUserWithEmailAndPassword(email, password)
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
                                Timber.tag("AuthViewModel")
                                    .d("User profile is successfully created for user %s", user)
                                onSignedUp(user)
                                registerStatus.postValue(true)
                            }
                            .addOnFailureListener { exception ->
                                Timber.tag("AuthViewModel")
                                    .e("Failed to create user profile: %s", exception.message)
                                errorLiveData.postValue("Failed to create user profile: ${exception.message}")
                            }
                    } ?: run {
                        errorLiveData.postValue("User is null after creation")
                    }
                } else {
                    Timber.tag("AuthViewModel").e("Sign up failed: %s", task.exception?.message)
                    errorLiveData.postValue("Sign up failed: ${task.exception?.message}")
                }
            }
    }
}