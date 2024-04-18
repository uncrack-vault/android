package com.geekymusketeers.uncrack.data.remote.request

data class AuthRequest(
    val name: String,
    val email: String,
    val password: String
)
