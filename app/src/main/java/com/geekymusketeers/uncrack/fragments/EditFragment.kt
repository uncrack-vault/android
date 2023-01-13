package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.FragmentEditBinding
import com.geekymusketeers.uncrack.viewModel.AccountViewModel


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!



    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater,container,false)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)


        binding.btnEdit.setOnClickListener {
            updateDB()
        }


        return binding.root
    }

    private fun updateDB() {

    }


}