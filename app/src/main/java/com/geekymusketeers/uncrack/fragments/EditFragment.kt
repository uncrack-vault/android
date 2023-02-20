package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var selectedAccount: String? =null

    private val args by navArgs<EditFragmentArgs>()

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var editViewModel: AddEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater,container,false)

        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        editViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[AddEditViewModel::class.java]


        // Fetching data from adapter
        val id = arguments?.getInt("id")
        val acc = arguments?.getString("company").toString()
        binding.editAccType.text = acc
        val email = arguments?.getString("email").toString()
        binding.editEmail.setText(email)
        val username = arguments?.getString("username").toString()
        binding.editUsername.setText(username)
        val pass = arguments?.getString("password").toString()
        binding.editPassword.setText(pass)
        val category = arguments?.getString("category").toString()
        binding.editCategoryChipGroup.children.toList().filter {
            (it as Chip).isChecked
        }


        val account = id?.let { Account(it,acc,email,category,username,pass) }

        // Setting logo according to the account type

        when(acc?.toLowerCase().toString()){
            "paypal" -> setImageOnAccountNameChange(R.drawable.paypal)
            "instagram" -> setImageOnAccountNameChange(R.drawable.instagram)
            "facebook" -> setImageOnAccountNameChange(R.drawable.facebook)
            "linkedin" -> setImageOnAccountNameChange(R.drawable.linkedin)
            "snapchat" -> setImageOnAccountNameChange(R.drawable.snapchat)
            "youtube" -> setImageOnAccountNameChange(R.drawable.youtube)
            "dropbox" -> setImageOnAccountNameChange(R.drawable.dropbox)
            "twitter" -> setImageOnAccountNameChange(R.drawable.twitter)
            "google drive" -> setImageOnAccountNameChange(R.drawable.drive)
            "netflix" -> setImageOnAccountNameChange(R.drawable.netflix_logo)
            "amazon prime" -> setImageOnAccountNameChange(R.drawable.amazon_logo)
            "spotify" -> setImageOnAccountNameChange(R.drawable.spotify)
            "discord" -> setImageOnAccountNameChange(R.drawable.discord)
            "others" -> setImageOnAccountNameChange(R.drawable.general_account)
        }

        // Setting logo according to the user choice when he want to edit the account ype



        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        binding.btnEdit.setOnClickListener {

            val accountType = binding.editAccType.text.toString()
            val email = binding.editEmail.text.toString()

            if (accountType.isEmpty()){
                Snackbar.make(binding.root, "Please select the account type", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (email.isEmpty()){
                Snackbar.make(binding.root, "Please enter the Email Id", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateDB(account)
        }

        binding.editBack.setOnClickListener {

            backButton()
        }
        return binding.root
    }

    private fun setImageOnAccountNameChange(imageID: Int) {
        binding.accountLogo.apply {
            setImageResource(imageID)
        }
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
                        R.color.white
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
                backButton()
                true
            } else false
        }
    }

    private fun updateDB(account: Account?) {

        val accountName = binding.editAccType.text.toString()
        val email = binding.editEmail.text.toString()
        val category: String = (binding.editCategoryChipGroup.children.toList().filter {
            (it as Chip).isChecked
        }[0] as Chip).text.toString()
        val password = binding.editPassword.text.toString()
        val userName = binding.editUsername.text.toString()

        if (account!=null){
            if (accountName != account.company){
                account.company = accountName
            }
            if (email != account.email){
                account.email = email
            }
            if (category != account.category){
                account.category = category
            }
            if (password != account.password){
                account.password = password
            }
            if (userName != account.username){
                account.username = userName
            }
            lifecycleScope.launch {
                editViewModel.updateData(accountViewModel,account)
            }
        }

//        accountViewModel.editAccount(updateAccount)

        editViewModel.updateStatus.observe(viewLifecycleOwner){

            if (it == 1){
                Toast.makeText(requireContext(),"Successfully Updated",Toast.LENGTH_LONG).show()
            }
            else if (it == 5){
                Toast.makeText(requireContext(),"Failed to edit",Toast.LENGTH_LONG).show()
            }

        }
        val frag = HomeFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()

    }



}

