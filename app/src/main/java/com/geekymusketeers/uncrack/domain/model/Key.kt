package com.geekymusketeers.uncrack.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "master_key")
data class Key(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val password : String
)