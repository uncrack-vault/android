package com.geekymusketeers.uncrack.adapter


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.fragments.AddFragment
import com.geekymusketeers.uncrack.fragments.EditFragment
import com.geekymusketeers.uncrack.fragments.HomeFragment
import com.geekymusketeers.uncrack.fragments.ViewPasswordFragment
import com.geekymusketeers.uncrack.model.Account
import com.google.android.material.card.MaterialCardView

class AccountAdapter(
    private val mContext: Context
): RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

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

        // PopUpMenu function

        holder.itemView.findViewById<ImageView>(R.id.Options).setOnClickListener {

            val popUpMenu = PopupMenu(mContext,it)
            popUpMenu.menuInflater.inflate(R.menu.option_menu,popUpMenu.menu)


            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.miEdit -> {
                        // code here
                        val bundle = Bundle()
                        bundle.putParcelable("account",currentAccount)
                        val editFragment = EditFragment()
                        editFragment.arguments = bundle
                        val transaction = (it.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment,EditFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        true
                    }
                    R.id.miDelete -> {
                        // code here
                        true
                    }
                    else -> false
                }

            }
            popUpMenu.show()
        }


        holder.itemView.findViewById<MaterialCardView>(R.id.card).setOnClickListener { view ->

            val transaction = (view.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment,ViewPasswordFragment())
            transaction.addToBackStack(null)
            transaction.commit()
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