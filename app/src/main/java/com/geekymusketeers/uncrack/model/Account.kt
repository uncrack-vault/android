package com.geekymusketeers.uncrack.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val company : String,
    val email : String,
    val password : String

) : Parcelable