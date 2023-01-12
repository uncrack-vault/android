package com.geekymusketeers.uncrack.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Account
class AccountAdapter: RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private var accountList = emptyList<Account>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAccount = accountList[position]

        // Setting company name and category in card view

        holder.itemView.findViewById<TextView>(R.id.txtCompany).text = currentAccount.company
        holder.itemView.findViewById<TextView>(R.id.txtCategory).text = currentAccount.category

        //  For setting icons of company according to users choice

        when (currentAccount.company.toLowerCase().trim()) {
            "paypal" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.paypal)
            "instagram" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.instagram)
            "facebook" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.facebook)
            "linkedin" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.linkedin)
            "snapchat" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.snapchat)
            "twitter" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.twitter)
            "behance" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.behance)
            "google drive" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.drive)
            "spotify" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.spotify)
            "discord" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.discord)
        }




        holder.itemView.findViewById<ConstraintLayout>(R.id.card_layout).setOnClickListener {
//            val action = Home
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    fun setData(account: List<Account>){
        this.accountList = account
        notifyDataSetChanged()
    }
}