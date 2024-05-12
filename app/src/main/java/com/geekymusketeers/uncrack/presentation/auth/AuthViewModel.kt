package com.geekymusketeers.uncrack.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.uncrack.domain.model.User
import com.geekymusketeers.uncrack.util.UtilsKt
import com.geekymusketeers.uncrack.util.UtilsKt.validateEmail
import com.geekymusketeers.uncrack.util.UtilsKt.validateName
import com.geekymusketeers.uncrack.util.UtilsKt.validatePassword
import com.geekymusketeers.uncrack.util.runIO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val _isRegistrationEnabled = MutableLiveData(false)
    val isRegistrationEnabled: LiveData<Boolean> = _isRegistrationEnabled

    private val _isSignInButtonEnabled = MutableLiveData(false)
    val isSignInButtonEnabled: LiveData<Boolean> = _isSignInButtonEnabled

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun setUserName(username: String) {
        _username.value = username
        checkIfAdded()
    }

    fun setEmail(email: String) {
        _email.value = email
        checkIfAdded()
    }

    fun setPassword(password: String) {
        _password.value = password
        checkIfAdded()
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

    private fun checkIfAdded() {
        val isUserNameValid = validateName(username.value?.trim())
        val isEmailValid = validateEmail(email.value?.trim())
        val isInputsNullOrEmpty = username.value.isNullOrEmpty() && email.value.isNullOrEmpty() && password.value.isNullOrEmpty() && isUserNameValid && isEmailValid

        _isRegistrationEnabled.value = !isInputsNullOrEmpty
        _isSignInButtonEnabled.value = !isInputsNullOrEmpty
    }
}