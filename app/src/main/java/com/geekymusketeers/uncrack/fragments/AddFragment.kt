package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.helper.Util
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class AddFragment : Fragment() {

    private  var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddFragmentArgs>()
    private var selectedAccount: String? =null

    private lateinit var viewModel : AccountViewModel
    private lateinit var myViewModel: AddEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        myViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[AddEditViewModel::class.java]

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE


        binding.accType.afterTextChanged{
            selectedAccount = it
            when(it.toLowerCase()){
                "paypal" -> setImageOnAccountNameChange(R.drawable.paypal)
                "instagram" -> setImageOnAccountNameChange(R.drawable.instagram)
                "facebook" -> setImageOnAccountNameChange(R.drawable.facebook)
                "linkedin" -> setImageOnAccountNameChange(R.drawable.linkedin)
                "snapchat" -> setImageOnAccountNameChange(R.drawable.snapchat)
                "youtube" -> setImageOnAccountNameChange(R.drawable.youtube)
                "dropbox" -> setImageOnAccountNameChange(R.drawable.dropbox)
                "twitter" -> setImageOnAccountNameChange(R.drawable.twitter)
                "google drive" -> setImageOnAccountNameChange(R.drawable.drive)
                "netflix" -> {
//                    binding.btnShare.visibility = View.VISIBLE
                    setImageOnAccountNameChange(R.drawable.netflix_logo)
                }
                "amazon prime" -> {
//                    binding.btnShare.visibility = View.VISIBLE
                    setImageOnAccountNameChange(R.drawable.amazon_logo)
                }
                "spotify" -> setImageOnAccountNameChange(R.drawable.spotify)
                "discord" -> setImageOnAccountNameChange(R.drawable.discord)
                "others" -> setImageOnAccountNameChange(R.drawable.general_account)
            }
        }

        binding.btnSave.setOnClickListener {

            val company = binding.accType.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (company.isEmpty() || email.isEmpty() || password.isEmpty()){
                Snackbar.make(binding.root, "Please fill all the details", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Snackbar.make(binding.root, "Please check your Email Id", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            insertDataToDB()

        }
        binding.back.setOnClickListener {

            handleBackButtonPress()

        }
        // Account List
        val accounts = resources.getStringArray(R.array.accounts)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.list_items,accounts)
        binding.accType.setAdapter(arrayAdapter)

        return binding.root

    }

    private fun setImageOnAccountNameChange(imageID: Int){
        binding.accountLogo.apply {
            setImageResource(imageID)
            visibility = View.VISIBLE
        }
        binding.remainingLayout.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
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

//        viewModel.addAccount(account)
        lifecycleScope.launch {
            myViewModel.saveData(viewModel,account)
        }

        Snackbar.make(binding.root, "Successful Saved", Snackbar.LENGTH_SHORT).show()
       // Moving into HomeFragment after saving
        val frag = HomeFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }

    private fun AutoCompleteTextView.afterTextChanged(afterTextChanged: (String) -> Unit){

        this.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

        })

    }
}