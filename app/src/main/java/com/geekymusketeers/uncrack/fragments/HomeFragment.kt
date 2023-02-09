package com.geekymusketeers.uncrack.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.adapter.AccountAdapter
import com.geekymusketeers.uncrack.databinding.FragmentHomeBinding
import com.geekymusketeers.uncrack.databinding.ViewpasswordModalBinding
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel
    private lateinit var adapter: AccountAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE

        adapter = AccountAdapter(requireContext()){ currentAccount ->

            val dialog = ViewpasswordModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()

            dialog.apply {

                accountName.text = currentAccount.company
                accountEmail.text = currentAccount.email
                accountUsername.text = currentAccount.username
                accountPassword.text = currentAccount.password

                when (currentAccount.company.toLowerCase().trim()) {

                    "paypal" -> accountLogo.setImageResource(R.drawable.paypal)
                    "instagram" -> accountLogo.setImageResource(R.drawable.instagram)
                    "facebook" -> accountLogo.setImageResource(R.drawable.facebook)
                    "linkedin" -> accountLogo.setImageResource(R.drawable.linkedin)
                    "snapchat" -> accountLogo.setImageResource(R.drawable.snapchat)
                    "gmail" -> accountLogo.setImageResource(R.drawable.gmail)
                    "twitter" -> accountLogo.setImageResource(R.drawable.twitter)
                    "google drive" -> accountLogo.setImageResource(R.drawable.drive)
                    "netflix" -> accountLogo.setImageResource(R.drawable.netflix_logo)
                    "amazon prime" -> accountLogo.setImageResource(R.drawable.amazon_logo)
                    "spotify" -> accountLogo.setImageResource(R.drawable.amazon)
                    "discord" -> accountLogo.setImageResource(R.drawable.discord)
                    "others" -> accountLogo.setImageResource(R.drawable.general_account)
                }
            }

            dialog.root.setBottomSheet(bottomSheet)

        }
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { account ->
            adapter.setData(account)
            if (account.isEmpty()){
                binding.emptyList.visibility = View.VISIBLE
            }else{
                binding.emptyList.visibility = View.GONE
            }

        })


        // Moving to AddFragment
        binding.fab.setOnClickListener { view ->
            val fragment = AddFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment,fragment)?.addToBackStack( "tag" )?.commit()
        }
        setUpFab()
//        popup_menu()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
            viewModel.readAllData.observe(viewLifecycleOwner, Observer { account ->
                adapter.setData(account)
                if (account.isEmpty()){
                    binding.emptyList.visibility = View.VISIBLE
                }else{
                    binding.emptyList.visibility = View.GONE
                }

            })
    }

    private fun setUpFab() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabText.visibility == View.VISIBLE) {
                    binding.fabText.visibility = View.GONE
                } else if (dy < 0 && binding.fabText.visibility != View.VISIBLE) {
                    binding.fabText.visibility = View.VISIBLE
                }
            }
        })
    }


}