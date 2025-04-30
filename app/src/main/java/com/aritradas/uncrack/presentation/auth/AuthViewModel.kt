package com.aritradas.uncrack.presentation.auth

import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritradas.uncrack.domain.model.User
import com.aritradas.uncrack.util.HashUtils
import com.aritradas.uncrack.util.runIO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    val resetPassword = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val registerStatus = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()
    val passkeyLoginSuccess = MutableLiveData<FirebaseUser?>()

    fun resetPassword(email: String) = runIO {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnSuccessListener {
                resetPassword.postValue(true)
            }.addOnFailureListener {
                errorLiveData.postValue(it.message.toString())
            }
    }

    fun logIn(email: String, password: String) = runIO {
        delay(2000L)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userDB = FirebaseFirestore.getInstance()
                        userDB.collection("Users")
                            .document(it.uid)
                            .get()
                            .addOnSuccessListener { document ->
                                val storedHashedPassword = document.getString("password")

                                if (storedHashedPassword != null) {
                                    if (HashUtils.checkHashPassword(password, storedHashedPassword)) {
                                        loginSuccess.postValue(true)
                                    } else {
                                        errorLiveData.postValue("Invalid email or password")
                                        loginSuccess.postValue(false)
                                    }
                                } else {
                                    errorLiveData.postValue("Failed to retrieve hashed password")
                                }
                            }
                            .addOnFailureListener {
                                errorLiveData.postValue("Failed to retrieve user data")
                            }
                    } ?: run {
                        errorLiveData.postValue("User not found")
                        loginSuccess.postValue(false)
                    }
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
        delay(2000L)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val hashedPassword = HashUtils.hasPassword(password)
                    val userProfile = User(name, email, hashedPassword)

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

    fun createPasskey(
        credentialManager: CredentialManager,
        userName: String,
        userEmail: String,
        context: android.content.Context,
        onSuccess: (FirebaseUser) -> Unit
    ) = runIO {
        try {
            val user = auth.currentUser
            if (user == null) {
                errorLiveData.postValue("No authenticated user found")
                return@runIO
            }

            // Construct the passkey creation request
            val requestJson = JSONObject().apply {
                put(
                    "challenge",
                    "YOUR_BASE64_ENCODED_CHALLENGE"
                ) // Replace with a server-generated challenge
                put("rp", JSONObject().apply {
                    put("name", "UnCrack")
                    put("id", "your-app-domain.com") // Replace with your app's domain
                })
                put("user", JSONObject().apply {
                    put("id", user.uid.toByteArray().toBase64())
                    put("name", userEmail)
                    put("displayName", userName)
                })
                put("pubKeyCredParams", JSONArray().apply {
                    put(JSONObject().apply {
                        put("type", "public-key")
                        put("alg", -7) // ES256 algorithm
                    })
                })
                put("authenticatorSelection", JSONObject().apply {
                    put("authenticatorAttachment", "platform")
                    put("userVerification", "required")
                })
            }

            val createRequest = CreatePublicKeyCredentialRequest(
                requestJson = requestJson.toString()
            )

            val result = credentialManager.createCredential(context, createRequest)
            Timber.tag("AuthViewModel").d("Passkey created successfully for user: $userEmail")

            // Save user profile to Firestore
            val userProfile = User(userName, userEmail, "") // No password for passkey
            val userDB = FirebaseFirestore.getInstance()
            userDB.collection("Users")
                .document(user.uid)
                .set(userProfile)
                .addOnSuccessListener {
                    Timber.tag("AuthViewModel").d("User profile created for passkey user")
                    onSuccess(user)
                    registerStatus.postValue(true)
                }
                .addOnFailureListener { exception ->
                    Timber.tag("AuthViewModel")
                        .e("Failed to create user profile: ${exception.message}")
                    errorLiveData.postValue("Failed to create user profile: ${exception.message}")
                }
        } catch (e: Exception) {
            Timber.tag("AuthViewModel").e("Passkey creation failed: ${e.message}")
            errorLiveData.postValue("Passkey creation failed: ${e.message}")
        }
    }

    // Authenticate with a passkey
    fun authenticateWithPasskey(
        credentialManager: CredentialManager,
        context: android.content.Context
    ) = runIO {
        try {
            val requestJson = JSONObject().apply {
                put(
                    "challenge",
                    "YOUR_BASE64_ENCODED_CHALLENGE"
                ) // Replace with a server-generated challenge
                put("rpId", "your-app-domain.com") // Replace with your app's domain
                put("allowCredentials", JSONArray())
            }

            val getCredentialOption = GetPublicKeyCredentialOption(
                requestJson = requestJson.toString()
            )

            val getRequest = GetCredentialRequest(listOf(getCredentialOption))
            val result = credentialManager.getCredential(context, getRequest)
            val credential = result.credential

            // Verify the passkey (you may need server-side verification)
            if (credential.type == "public-key") {
                // Cast the credential to PublicKeyCredential to access its properties
                val publicKeyCredential = credential as? PublicKeyCredential
                val userId = publicKeyCredential?.id ?: run {
                    errorLiveData.postValue("Failed to extract credential ID")
                    return@runIO
                }

                val userDB = FirebaseFirestore.getInstance()
                userDB.collection("Users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val user = auth.currentUser
                            if (user != null) {
                                passkeyLoginSuccess.postValue(user)
                            } else {
                                errorLiveData.postValue("User not authenticated")
                            }
                        } else {
                            errorLiveData.postValue("User not found")
                        }
                    }
                    .addOnFailureListener {
                        errorLiveData.postValue("Failed to retrieve user data")
                    }
            } else {
                errorLiveData.postValue("Invalid credential type")
            }
        } catch (e: Exception) {
            Timber.tag("AuthViewModel").e("Passkey authentication failed: ${e.message}")
            errorLiveData.postValue("Passkey authentication failed: ${e.message}")
        }
    }

    // Utility to encode byte array to Base64
    private fun ByteArray.toBase64(): String {
        return android.util.Base64.encodeToString(this, android.util.Base64.NO_WRAP)
    }
}