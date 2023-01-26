package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.FragmentEditBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.helper.Util
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!


    private val args by navArgs<EditFragmentArgs>()

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater,container,false)

        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        binding.editAccType.setText(args.account.company)
        binding.editEmail.setText(args.account.email)
        binding.editUsername.setText(args.account.username)
        binding.editPassword.setText(args.account.password)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        binding.btnEdit.setOnClickListener {
            updateDB()
        }

        binding.editBack.setOnClickListener {

            backButton()
        }
        return binding.root
    }

    private fun backButton() {

        val editAccountType = binding.editAccType.text.toString().trim()
        val editEmail = binding.editEmail.text.toString().trim()
        val editUserName = binding.editUsername.text.toString().trim()
        val editPassword = binding.editPassword.text.toString().trim()

        if (editAccountType.isNotEmpty() || editEmail.isNotEmpty() || editUserName.isNotEmpty() || editPassword.isNotEmpty()) {

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
            findNavController().navigate(R.id.action_editFragment_to_homeFragment)
        }
    }

    private fun updateDB() {

        val accountName = binding.editAccType.text.toString()
        val email = binding.editEmail.text.toString()
        val category: String = (binding.editCategoryChipGroup.children.toList().filter {
            (it as Chip).isChecked
        }[0] as Chip).text.toString()
        val password = binding.editPassword.text.toString()
        val userName = binding.editUsername.text.toString()

        val updateAccount = Account(0,accountName, email, category,userName, password)

        accountViewModel.editAccount(updateAccount)

        findNavController().navigate(R.id.action_editFragment_to_homeFragment)

    }


}