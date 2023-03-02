package com.geekymusketeers.uncrack.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardNumber: String,
    val cardHolderName: String,
    val expirationDate: String,
    val cvv: String

) : Parcelable