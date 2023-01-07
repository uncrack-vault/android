package com.geekymusketeers.uncrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.fragments.HomeFragment
import com.geekymusketeers.uncrack.model.Account
import com.google.android.material.card.MaterialCardView

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

        holder.itemView.findViewById<TextView>(R.id.txtCompany).text = currentAccount.company
        holder.itemView.findViewById<TextView>(R.id.txtPassword).text = currentAccount.password

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