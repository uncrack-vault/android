package com.geekymusketeers.uncrack.ui.fragments.account

import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.FragmentEditBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.databinding.EditpasswordModalBinding
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.SecretKey


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var editViewModel: AddEditViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater,container,false)

        initialization()
        initObservers()

        // Fetching data from adapter
        val id = arguments?.getInt("id")
        val acc = arguments?.getString("company").toString()
        binding.editAccType.text = acc
        val email = arguments?.getString("email").toString()
        binding.editEmail.setText(email)
        val username = arguments?.getString("username").toString()
        binding.editUsername.setText(username)
        val pass = arguments?.getString("password").toString()
        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val decryptedPassword = encryption.decryptOrNull(pass)
        binding.editPassword.setText(decryptedPassword)
        val note = arguments?.getString("note").toString()
        binding.editNote.setText(note)
        val dateTime = arguments?.getString("dateTime").toString()
        val category = arguments?.getString("category").toString()
        for (child in binding.editCategoryChipGroup.children) {
            if (child is Chip) {
                // Check if the Chip's text matches the category from arguments
                if (child.text == category) {
                    // Set the Chip as checked
                    child.isChecked = true
                    break // Exit the loop since the correct Chip has been found
                }
            }
        }

        val account = id?.let { Account(it,acc,email,category,username,pass,note,dateTime) }

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
            "github" -> setImageOnAccountNameChange(R.drawable.cl_github)
            "gmail" -> setImageOnAccountNameChange(R.drawable.gmail)
            "paytm" -> setImageOnAccountNameChange(R.drawable.cl_paytm)
            "quora" -> setImageOnAccountNameChange(R.drawable.cl_quora)
            "reddit" -> setImageOnAccountNameChange(R.drawable.cl_reddit)
            "others" -> setImageOnAccountNameChange(R.drawable.general_account)
        }


        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        buttonLayout.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val pass = binding.editPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(requireContext(),"Please check your Email Id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (email.isEmpty() && pass.isEmpty()){
                binding.apply {
                    editEmailHelperTV.text = "Please Enter the Email ID"
                    editEmailHelperTV.visibility = View.VISIBLE
                    editPasswordHelperTV.text = "Please Enter the Password"
                    editPasswordHelperTV.visibility = View.VISIBLE
                }
                return@setOnClickListener
            }else if (email.isEmpty()){
                binding.apply {
                    editEmailHelperTV.text = "Please Enter the Email ID"
                    editEmailHelperTV.visibility = View.VISIBLE
                }
            }else if (pass.isEmpty()){
                binding.apply {
                    editPasswordHelperTV.text = "Please Enter the Password"
                    editPasswordHelperTV.visibility = View.VISIBLE
                }
            }
            val saveDialog = EditpasswordModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()

            saveDialog.apply {
                optionsHeading.text = getString(R.string.confirm_changes)
                optionsContent.text = getString(R.string.update_text)
                positiveOption.text = getString(R.string.save_changes)
                negativeOption.text = getString(R.string.dont_save)

                positiveOption.setOnClickListener {
                    bottomSheet.dismiss()
                    binding.progressAnimation.progressParent.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.Main){
                        delay(1000L)
                        updateDB(account)
                        transaction()
                    }

                }
                negativeOption.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }
            saveDialog.root.setBottomSheet(bottomSheet)

        }

        binding.editBack.setOnClickListener {

            backButton()
        }

        // Setting FLAG_SECURE to prevent screenshots
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Clearing FLAG_SECURE when the fragment is destroyed
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    private fun transaction() {
        val frag = HomeFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }


    private fun initialization() {
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        editViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[AddEditViewModel::class.java]
        binding.apply {
            btnEdit.apply {
                this@EditFragment.buttonLayout = this.progressButtonBg
                this@EditFragment.buttonText = this.buttonText
                this@EditFragment.buttonText.text = getString(R.string.save_details)
                this@EditFragment.buttonProgress = this.buttonProgress
            }
        }
    }


    private fun initObservers() {
        editViewModel.updateStatus.observe(viewLifecycleOwner){

            if (it == 1){
                Toast.makeText(requireContext(),"Successfully Updated",Toast.LENGTH_SHORT).show()
                val frag = HomeFragment()
                val trans = fragmentManager?.beginTransaction()
                trans?.replace(R.id.fragment,frag)?.commit()
            }
            else if (it == 5){
                Toast.makeText(requireContext(),"Failed to edit",Toast.LENGTH_SHORT).show()
            }

        }

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

        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedEditedPassword = encryption.encryptOrNull(password)

        val userName = binding.editUsername.text.toString()
        val notes = binding.editNote.text.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

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
            if (encryptedEditedPassword != account.password){
                account.password = encryptedEditedPassword
            }
            if (userName != account.username){
                account.username = userName
            }
            if (notes != account.note){
                account.note = notes
            }
            if (currentDate != account.dateTime){
                account.dateTime = currentDate
            }
            lifecycleScope.launch {
                editViewModel.updateData(accountViewModel,account)
            }
        }
    }
}

