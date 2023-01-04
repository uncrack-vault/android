package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.viewModel.AccountViewModel


class AddFragment : Fragment() {

    private  var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        binding.btnSave.setOnClickListener {
            insertDataToDB()
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        // Account List
        val accounts = resources.getStringArray(R.array.accounts)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.list_items,accounts)
        binding.accType.setAdapter(arrayAdapter)

        return binding.root
    }



    private fun insertDataToDB() {
        val company = binding.accType.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (inputCheck(company,email,password)){
            val account = Account(0,company, email, password)

            viewModel.addAccount(account)
            Toast.makeText(requireContext(),"Successfully Saved",Toast.LENGTH_LONG).show()
            // Moving into HomeFragment after saving
            val frag = HomeFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputCheck(company: String,email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(company)&& TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
    }

}