package com.geekymusketeers.uncrack.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var company : String,
    var email : String,
    var category : String,
    var username : String,
    var password : String,
    var note : String,
    var dateTime : String,
    var isFavourite : Boolean = false
) : Parcelable