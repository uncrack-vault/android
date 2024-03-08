package com.geekymusketeers.uncrack.domain.repository

import androidx.lifecycle.LiveData
import com.geekymusketeers.uncrack.domain.model.Card
import com.geekymusketeers.uncrack.data.dao.CardDao

class CardRepository(private val cardDao: CardDao) {
    val readAllCard: LiveData<List<Card>> = cardDao.readAllCard()

    suspend fun addCard(card: Card){
        cardDao.addCard(card)
    }

    suspend fun editCard(card: Card){
        cardDao.editCard(card)
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card)
    }

}