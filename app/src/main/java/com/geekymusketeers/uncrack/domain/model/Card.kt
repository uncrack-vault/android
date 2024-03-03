package com.geekymusketeers.uncrack.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardType: String,
    val cardNumber: String,
    val cardHolderName: String,
    val expirationMonth: String,
    val expirationYear: String,
    val cvv: String

) : Parcelable