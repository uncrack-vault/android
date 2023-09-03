package com.geekymusketeers.uncrack.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Card
import com.geekymusketeers.uncrack.util.Encryption
import com.google.android.material.card.MaterialCardView
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

        val decryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val decryptedNumber = decryption.decryptOrNull(currentCard.cardNumber)
        val maskedCardNumber = "**** **** **** ** " + decryptedNumber.takeLast(2)
        val decryptedMonth = decryption.decryptOrNull(currentCard.expirationMonth)
        val decryptedYear = decryption.decryptOrNull(currentCard.expirationYear)


        holder.itemView.findViewById<TextView>(R.id.card_number).text = maskedCardNumber
        holder.itemView.findViewById<TextView>(R.id.month).text = decryptedMonth
        holder.itemView.findViewById<TextView>(R.id.year).text = decryptedYear
        holder.itemView.findViewById<TextView>(R.id.card_holder_name).text = currentCard.cardHolderName

        when(currentCard.cardType.toLowerCase().trim()){

            "visa" -> holder.itemView.apply {
                findViewById<MaterialCardView>(R.id.atm_card).background = ContextCompat.getDrawable(
                    context,
                    R.drawable.visa_bg
                )
                findViewById<ImageView>(R.id.type).setImageResource(R.drawable.ic_visa)
            }
            "mastercard" -> {
                holder.itemView.apply {
                    findViewById<MaterialCardView>(R.id.atm_card).background = ContextCompat.getDrawable(
                        context,
                        R.drawable.mastercard_bg
                    )
                    findViewById<ImageView>(R.id.type).setImageResource(R.drawable.ic_mastercard)
                }
            }
            "rupay" -> {
                holder.itemView.apply {
                    findViewById<MaterialCardView>(R.id.atm_card).background = ContextCompat.getDrawable(
                        context,
                        R.drawable.rupay_bg
                    )

                    findViewById<ImageView>(R.id.type).setImageResource(R.drawable.rupay_logo)
                }
            }
            "american express" -> {
                holder.itemView.apply {
                    findViewById<MaterialCardView>(R.id.atm_card).background = ContextCompat.getDrawable(
                        context,
                        R.drawable.americanexpress_bg
                    )

                    findViewById<ImageView>(R.id.type).setImageResource(R.drawable.amex_logo)
                }
            }
        }

    }

    fun setCardData(card: List<Card>){
        cardList = card
        notifyDataSetChanged()
    }
}