package com.geekymusketeers.uncrack.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val company : String,
    val email : String,
    val username : String,
    val password : String
) : Parcelable