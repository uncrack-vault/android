package com.geekymusketeers.uncrack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.geekymusketeers.uncrack.domain.model.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card: Card)

    @Update
    suspend fun editCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * FROM card_table ORDER BY id ASC")
    fun readAllCard(): LiveData<List<Card>>
}