package com.geekymusketeers.uncrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Card
import java.util.EventListener

class CardAdapter(private val context: Context,
    private val listener: (Card) -> Unit):
    RecyclerView.Adapter<CardAdapter.ViewHolder>()
{
    private var cardList = emptyList<Card>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentCard = cardList[position]
        holder.itemView.setOnClickListener {
            listener(currentCard)

        }
        holder.itemView.findViewById<TextView>(R.id.card_number).text = currentCard.cardNumber
        holder.itemView.findViewById<TextView>(R.id.month).text = currentCard.expirationMonth
        holder.itemView.findViewById<TextView>(R.id.year).text = currentCard.expirationYear
        holder.itemView.findViewById<TextView>(R.id.card_holder_name).text = currentCard.cardHolderName

    }

    fun setCardData(card: List<Card>){
        cardList = card
        notifyDataSetChanged()
    }
}