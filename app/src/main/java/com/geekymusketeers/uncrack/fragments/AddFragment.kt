package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding.btnSave.setOnClickListener {
            insertDatatoDB()
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        return binding.root
    }

    private fun insertDatatoDB() {

        val Email = binding.email.text.toString()
        val Password = binding.password.text.toString()

        if (inputCheck(Email,Password)){

//            val account = Account(0, Email,Password)

        }
    }

    private fun inputCheck(email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
    }

}