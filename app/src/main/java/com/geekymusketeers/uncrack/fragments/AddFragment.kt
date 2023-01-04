package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener


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

        return binding.root

        val adapter = IconSpinnerAdapter(binding.accType)
        binding.accType.setOnSpinnerItemSelectedListener(
            OnSpinnerItemSelectedListener<IconSpinnerItem> { _, _, _, item ->
                Toast.makeText(requireContext(), item.text, Toast.LENGTH_SHORT).show()
            }
        )
        adapter.setItems(
            arrayListOf(
                IconSpinnerItem(
                    iconRes = R.drawable.paypal,
                    text = "PayPal"
                ),
                IconSpinnerItem(
                    iconRes = R.drawable.facebook,
                    text = "Facebook"
                ),
                IconSpinnerItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.instagram),
                    text = "Instagram"
                ),
                IconSpinnerItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.linkedin),
                    text = "LinkedIn"
                ),
                IconSpinnerItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.twitter),
                    text = "Twitter"
                ),
                IconSpinnerItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.snapchat),
                    text = "Snapchat"
                )
            )
        )
        binding.accType.getSpinnerRecyclerView().adapter = adapter
    }




    private fun insertDataToDB() {
        val company = binding.accType.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (inputCheck(company,email,password)){
            val account = Account(0,company, email, password)

            viewModel.addAccount(account)
            Toast.makeText(requireContext(),"Successfully Saved",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputCheck(company: String,email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(company)&& TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
    }

}