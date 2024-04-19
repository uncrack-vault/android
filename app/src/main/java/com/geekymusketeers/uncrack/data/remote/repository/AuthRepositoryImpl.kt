package com.geekymusketeers.uncrack.data.remote.repository

import android.content.SharedPreferences
import com.geekymusketeers.uncrack.data.UnCrackApi
import com.geekymusketeers.uncrack.data.remote.request.AuthRequest
import com.geekymusketeers.uncrack.data.remote.request.AuthResult
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: UnCrackApi,
    private val sharedPref: SharedPreferences
): AuthRepository {
    override suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    name = name,
                    email = email,
                    password = password
                )
            )
            signIn(name, email, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(name: String, email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    name = name,
                    email = email,
                    password = password
                )
            )
            sharedPref.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = sharedPref.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}