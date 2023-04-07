package com.geekymusketeers.uncrack.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "master_key")
data class Key(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val password : String
)