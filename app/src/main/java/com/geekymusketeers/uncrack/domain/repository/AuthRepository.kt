package com.geekymusketeers.uncrack.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

    suspend fun loginUser(email:String, password: String): FirebaseUser? {
        return try {
            val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            authResult.user
        } catch (e: FirebaseAuthInvalidUserException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
            authResult.user
        } catch (e: FirebaseAuthUserCollisionException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}