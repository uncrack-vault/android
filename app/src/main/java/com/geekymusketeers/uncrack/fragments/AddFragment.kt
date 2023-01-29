package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar


class AddFragment : Fragment() {

    private  var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddFragmentArgs>()

    private lateinit var viewModel : AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        binding.btnSave.setOnClickListener {

            val company = binding.accType.text.toString()
            val category: String = (binding.categoryChipGroup.children.toList().filter {
                (it as Chip).isChecked
            }[0] as Chip).text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (company.isEmpty()){
                Toast.makeText(requireContext(),"Enter select company",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(requireContext(),"Please check your Email Id",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            insertDataToDB()

        }
        binding.back.setOnClickListener {

            handleBackButtonPress()

        }
        binding.btnDelete.setOnClickListener {

            deleteAccount()
        }
        // Account List
        val accounts = resources.getStringArray(R.array.accounts)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.list_items,accounts)
        binding.accType.setAdapter(arrayAdapter)

        return binding.root

    }

    private fun deleteAccount() {

            val accountId = args.account!!.id
            val email = binding.email.text.toString()
            val userName = binding.username.text.toString()
            val password = binding.password.text.toString()
            val category: String = (binding.categoryChipGroup.children.toList().filter {
                (it as Chip).isChecked
            }[0] as Chip).text.toString()
            val company = binding.accType.text.toString()

            Account(accountId, company, email, category, userName, password).also{
                viewModel.deleteAccount(it)
                findNavController().navigateUp()
            }
    }

    private fun handleBackButtonPress() {
        val inputAccountType = binding.accType.text.toString().trim()
        val inputEmail = binding.email.text.toString().trim()
        val inputUserName = binding.username.text.toString().trim()
        val inputPassword = binding.password.text.toString().trim()

        if (inputAccountType.isNotEmpty() || inputEmail.isNotEmpty() || inputUserName.isNotEmpty() || inputPassword.isNotEmpty()) {

            val dialog = OptionsModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()
            dialog.apply {

                optionsHeading.text = "Discard changes"
                optionsContent.text = "Are you sure you discard changes?"
                positiveOption.text = "Discard"
                positiveOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                negativeOption.text = "Continue editing"
                negativeOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                positiveOption.setOnClickListener {
                    bottomSheet.dismiss()
                    val frag = HomeFragment()
                    val trans = fragmentManager?.beginTransaction()
                    trans?.replace(R.id.fragment,frag)?.commit()
                }
                negativeOption.setOnClickListener {
                    bottomSheet.dismiss()

                }
            }
            dialog.root.setBottomSheet(bottomSheet)
        }
        else {
            val frag = HomeFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                handleBackButtonPress()
                true
            } else false
        }
    }


    private fun insertDataToDB() {
        val company = binding.accType.text.toString()
        val email = binding.email.text.toString()
        val category: String = (binding.categoryChipGroup.children.toList().filter {
            (it as Chip).isChecked
        }[0] as Chip).text.toString()
        val password = binding.password.text.toString()
        val userName = binding.username.text.toString()

        val account = Account(0,company, email, category,userName, password)

        viewModel.addAccount(account)
        Toast.makeText(requireContext(),"Successfully Saved",Toast.LENGTH_LONG).show()
            // Moving into HomeFragment after saving
        val frag = HomeFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }
}