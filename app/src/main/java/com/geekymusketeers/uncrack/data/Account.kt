package com.geekymusketeers.uncrack.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class Account(

    val id : Int = 0,
    val company : String,
    val email : String,
    val password : String

) : Parcelable