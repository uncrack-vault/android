package com.geekymusketeers.uncrack.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.fragments.*
import com.geekymusketeers.uncrack.model.Account
import com.google.android.material.card.MaterialCardView

class AccountAdapter(private val context: Context, private val listner: (Account) -> Unit): RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

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


        holder.itemView.setOnClickListener {

            listner(currentAccount)
        }

        holder.itemView.findViewById<TextView>(R.id.txtCompany).text = currentAccount.company
        holder.itemView.findViewById<TextView>(R.id.txtEmail).text = currentAccount.email

        //  For setting icons of company according to users choice

        when (currentAccount.company.toLowerCase().trim()) {

            "paypal" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.paypal)
            "instagram" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.instagram)
            "facebook" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.facebook)
            "linkedin" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.linkedin)
            "snapchat" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.snapchat)
            "gmail" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.gmail)
            "twitter" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.twitter)
            "google drive" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.drive)
            "netflix" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.netflix_logo)
            "amazon prime" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.amazon_logo)
            "spotify" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.amazon)
            "discord" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.discord)
            "others" -> holder.itemView.findViewById<ImageView>(R.id.img_company).setImageResource(R.drawable.general_account)
        }

        holder.itemView.findViewById<ImageView>(R.id.button_edit).setOnClickListener {

            // Passing data to Edit fragments
            val bundle = Bundle()
            bundle.putSerializable("category",currentAccount.category)
            bundle.putSerializable("id",currentAccount.id)
            bundle.putSerializable("company",currentAccount.company)
            bundle.putSerializable("email", currentAccount.email)
            bundle.putSerializable("username", currentAccount.username)
            bundle.putSerializable("password", currentAccount.password)
            bundle.putSerializable("category", currentAccount.category)

            val fragment = EditFragment()
            fragment.arguments = bundle
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

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