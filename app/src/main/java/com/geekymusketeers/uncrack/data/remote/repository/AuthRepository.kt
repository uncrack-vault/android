package com.geekymusketeers.uncrack.data.remote.repository

import com.geekymusketeers.uncrack.data.remote.request.AuthResult

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>
    suspend fun signIn(name: String, email: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}