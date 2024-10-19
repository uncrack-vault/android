package com.aritra.uncrack.domain.repository

import androidx.lifecycle.LiveData
import com.aritra.uncrack.domain.model.Card
import com.aritra.uncrack.data.dao.CardDao

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