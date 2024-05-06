package com.geekymusketeers.uncrack.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.User
import com.geekymusketeers.uncrack.util.UtilsKt
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val _isRegistrationEnabled = MutableLiveData(false)
    val isRegistrationEnabled: LiveData<Boolean> = _isRegistrationEnabled

    fun onFieldChanged(name: String, email: String, password: String) {
        validateFields(name, email, password)
    }

    private fun validateFields(name: String, email: String, password: String) {
        val isNameValid = UtilsKt.validateName(name)
        val isEmailValid = UtilsKt.validateEmail(email)
        val isPasswordValid = password.length >= 6

        _isRegistrationEnabled.value = isNameValid && isEmailValid && isPasswordValid
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
                    //
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
                            .addOnFailureListener {  }
                    }
                } else {
//                    onSignUpError("Please check your details")
                }
            }
    }
}