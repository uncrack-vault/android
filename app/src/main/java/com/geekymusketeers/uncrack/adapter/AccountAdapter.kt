package com.geekymusketeers.uncrack.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.databinding.ItemLayoutBinding
import com.geekymusketeers.uncrack.ui.fragments.account.EditFragment
import java.util.Locale

class AccountAdapter(private val context: Context,
                     private val listener: (Account) -> Unit):
    RecyclerView.Adapter<AccountAdapter.ViewHolder>()
{

    private var accountList = emptyList<Account>()

    class ViewHolder(val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAccount = accountList[position]


        holder.binding.root.setOnClickListener {

            listener(currentAccount)
        }

        holder.binding.apply { 
            txtCompany.text = currentAccount.company
            txtEmail.text = currentAccount.email
        }
        
        
        //  For setting icons of company according to users choice
        when (currentAccount.company.lowercase(Locale.ROOT).trim()) {

            "paypal" -> holder.binding.imgCompany.setImageResource(R.drawable.paypal)
            "instagram" -> holder.binding.imgCompany.setImageResource(R.drawable.instagram)
            "facebook" -> holder.binding.imgCompany.setImageResource(R.drawable.facebook)
            "linkedin" -> holder.binding.imgCompany.setImageResource(R.drawable.linkedin)
            "snapchat" -> holder.binding.imgCompany.setImageResource(R.drawable.snapchat)
            "youtube" -> holder.binding.imgCompany.setImageResource(R.drawable.youtube)
            "dropbox" -> holder.binding.imgCompany.setImageResource(R.drawable.dropbox)
            "twitter" -> holder.binding.imgCompany.setImageResource(R.drawable.twitter)
            "google drive" -> holder.binding.imgCompany.setImageResource(R.drawable.drive)
            "netflix" -> holder.binding.imgCompany.setImageResource(R.drawable.netflix_logo)
            "amazon prime" -> holder.binding.imgCompany.setImageResource(R.drawable.amazon_logo)
            "spotify" -> holder.binding.imgCompany.setImageResource(R.drawable.spotify)
            "discord" -> holder.binding.imgCompany.setImageResource(R.drawable.discord)
            "github" -> holder.binding.imgCompany.setImageResource(R.drawable.cl_github)
            "gmail" -> holder.binding.imgCompany.setImageResource(R.drawable.cl_gmail)
            "paytm" -> holder.binding.imgCompany.setImageResource(R.drawable.cl_paytm)
            "quora" -> holder.binding.imgCompany.setImageResource(R.drawable.cl_quora)
            "reddit" -> holder.binding.imgCompany.setImageResource(R.drawable.cl_reddit)
            "others" -> holder.binding.imgCompany.setImageResource(R.drawable.general_account)
        }


        holder.binding.buttonEdit.setOnClickListener {

            // Passing data to Edit fragments
            val bundle = Bundle()
            bundle.putSerializable("category",currentAccount.category)
            bundle.putSerializable("id",currentAccount.id)
            bundle.putSerializable("company",currentAccount.company)
            bundle.putSerializable("email", currentAccount.email)
            bundle.putSerializable("username", currentAccount.username)
            bundle.putSerializable("password", currentAccount.password)
            bundle.putSerializable("category", currentAccount.category)
            bundle.putSerializable("note",currentAccount.note)
            bundle.putSerializable("dateTime",currentAccount.dateTime)

            val fragment = EditFragment()
            fragment.arguments = bundle
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

    }

    override fun getItemCount() = accountList.size

    fun setData(account: List<Account>){
        accountList = account
        notifyDataSetChanged()
    }
}