package com.geekymusketeers.uncrack.ui.fragments.account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.VaultViewModel
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var selectedAccount: String? = null
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar

    private lateinit var viewModel: VaultViewModel
    private lateinit var myViewModel: AddEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        initialization()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        init()

        binding.accType.afterTextChanged {
            selectedAccount = it
            when (it.lowercase(Locale.ROOT)) {
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
        }

        binding.generateRandomPassword.setOnClickListener {
            val generatedPassword = generateRandomPassword(7) // length of the password
            binding.password.setText(generatedPassword)
            binding.password.setSelection(generatedPassword.length)
        }


        buttonLayout.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                binding.apply {
                    emailHelperTV.text = getString(R.string.please_enter_the_email_id)
                    emailHelperTV.visibility = View.VISIBLE
                    passwordHelperTV.text = getString(R.string.please_enter_the_password)
                    passwordHelperTV.visibility = View.VISIBLE
                }
                return@setOnClickListener
            } else if (email.isEmpty()) {
                binding.apply {
                    emailHelperTV.text = getString(R.string.please_enter_the_email_id)
                    emailHelperTV.visibility = View.VISIBLE
                }
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.apply {
                    passwordHelperTV.text = getString(R.string.please_enter_the_password)
                    passwordHelperTV.visibility = View.VISIBLE
                }
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Please check your Email Id", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            binding.progressAnimation.progressParent.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000L)
                insertDataToDB()
                transaction()
            }

        }
        binding.back.setOnClickListener {

            handleBackButtonPress()

        }
        // Account List
        val accounts = resources.getStringArray(R.array.accounts)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_items, accounts)
        binding.accType.setAdapter(arrayAdapter)

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

    private fun generateRandomPassword(length: Int): String {
        val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun transaction() {
        val frag = HomeFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment, frag)?.commit()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this)[VaultViewModel::class.java]
        myViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[AddEditViewModel::class.java]
        binding.apply {
            btnSave.apply {
                this@AddFragment.buttonLayout = this.progressButtonBg
                this@AddFragment.buttonText = this.buttonText
                this@AddFragment.buttonText.text = getString(R.string.save_details)
                this@AddFragment.buttonProgress = this.buttonProgress
            }
        }
    }

    private fun init() {
        myViewModel.saveStatus.observe(viewLifecycleOwner) {

            if (it == 2) {
                Toast.makeText(requireContext(), "Successful Saved", Toast.LENGTH_SHORT).show()
                // Moving into HomeFragment after saving
                val frag = HomeFragment()
                val trans = fragmentManager?.beginTransaction()
                trans?.replace(R.id.fragment, frag)?.commit()

            } else if (it == 6) {
                Toast.makeText(requireContext(), "Failed to save", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setImageOnAccountNameChange(imageID: Int) {
        binding.accountLogo.apply {
            setImageResource(imageID)
            visibility = View.VISIBLE
        }
        binding.remainingLayout.visibility = View.VISIBLE
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

                optionsHeading.text = getString(R.string.discard)
                optionsContent.text = getString(R.string.discard_text)
                positiveOption.text = getString(R.string.DISCARD)
                positiveOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                negativeOption.text = getString(R.string.continue_editing)
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
                    trans?.replace(R.id.fragment, frag)?.commit()
                }
                negativeOption.setOnClickListener {
                    bottomSheet.dismiss()

                }
            }
            dialog.root.setBottomSheet(bottomSheet)
        } else {
            val frag = HomeFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment, frag)?.commit()
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
        val note = binding.note.text.toString()
        val sdf = SimpleDateFormat("dd MMM yyyy, h:mma")
        val currentDate = sdf.format(Date())

        // Encrypt the password field
        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedPassword = encryption.encryptOrNull(password)

        val account =
            Account(0, company, email, category, userName, encryptedPassword, note, currentDate)

        lifecycleScope.launch {
            myViewModel.saveData(viewModel, account)
        }

    }

    private fun AutoCompleteTextView.afterTextChanged(afterTextChanged: (String) -> Unit) {

        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

        })

    }
}