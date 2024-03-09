package com.geekymusketeers.uncrack.ui.fragments.account

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import com.geekymusketeers.uncrack.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.adapter.AccountAdapter
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.databinding.FragmentHomeBinding
import com.geekymusketeers.uncrack.databinding.ParagraphModalBinding
import com.geekymusketeers.uncrack.databinding.SharepasswordModalBinding
import com.geekymusketeers.uncrack.databinding.ViewpasswordModalBinding
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.getBaseStringForFiltering
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel
    private lateinit var deleteViewModel: AddEditViewModel
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var recyclerView: RecyclerView
    private var accountList = mutableListOf<Account>()
    private var isPasswordVisible = false


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE

        deleteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[AddEditViewModel::class.java]



        accountAdapter = AccountAdapter(requireContext()) { currentAccount ->

            val dialog = ViewpasswordModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()

            dialog.apply {

                // Fetching data and setting it to textview and edittext
                accountName.text = currentAccount.company
                accountEmail.text = currentAccount.email
                accountUsername.text = "UserName:  " + currentAccount.username
                accountNote.text = "Note: " + currentAccount.note
                accountDateTime.text = "Last Updated: " + currentAccount.dateTime
                accountCategory.text = currentAccount.category
                when (accountCategory.text) {
                    "Work" -> categoryImage.setImageResource(R.drawable.work_icon)
                    "Social" -> categoryImage.setImageResource(R.drawable.social_icon)
                    "Mail" -> categoryImage.setImageResource(R.drawable.email_icon)
                    "Others" -> categoryImage.setImageResource(R.drawable.others_icon)
                }
                val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
                val decryptedViewPassword = encryption.decryptOrNull(currentAccount.password)
                accountPassword.setText(decryptedViewPassword)
                if (decryptedViewPassword != null) {
                    val passwordScore = calculatePasswordScore(decryptedViewPassword)
                    passwordScoreText.text = passwordScore.toString()
                    val mappedScore = (passwordScore * 100) / 9
                    circularProgressBar.setProgressWithAnimation(mappedScore.toFloat(), 3000)
                    when (passwordScore) {
                        in 0..3 -> {
                            val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
                            strengthLevel.setTextColor(colorRed)
                            circularProgressBar.progressBarColor = colorRed
                            strengthLevel.text = "Weak"
                        }

                        in 4..6 -> {
                            val colorYellow =
                                ContextCompat.getColor(requireContext(), R.color.yellow)
                            strengthLevel.setTextColor(colorYellow)
                            circularProgressBar.progressBarColor = colorYellow
                            strengthLevel.text = "Fair"
                        }

                        in 7..9 -> {
                            val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
                            strengthLevel.setTextColor(colorGreen)
                            circularProgressBar.progressBarColor = colorGreen
                            strengthLevel.text = "Strong"
                        }
                    }
                }

                // Show Password
                passwordToggle.setOnClickListener {
                    Util.hideKeyboard(requireActivity())
                    val showPasswordResId =
                        if (isPasswordVisible) R.drawable.visibility_on else R.drawable.visibility_off
                    isPasswordVisible = isPasswordVisible.not()
                    val passwordTransMethod = if (isPasswordVisible) null else PasswordTransformationMethod()

                    passwordToggle.setImageResource(showPasswordResId)
                    accountPassword.transformationMethod = passwordTransMethod
                }

                // Copy password to clipboard
                CopyPassword.setOnClickListener {
                    val clipboard: ClipboardManager? = ContextCompat.getSystemService(
                        requireContext(),
                        ClipboardManager::class.java
                    )
                    val clip =
                        ClipData.newPlainText("Copy Password", accountPassword.text.toString())
                    clipboard?.setPrimaryClip(clip)
                    Toast.makeText(
                        requireContext(),
                        "Password Copied to Clipboard",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Share button feature
                shareBtn.setOnClickListener {
                    bottomSheet.dismiss()
                    val shareDialog = SharepasswordModalBinding.inflate(layoutInflater)
                    val bottomShareSheet = requireContext().createBottomSheet()

                    shareDialog.apply {
                        optionsHeading.text = getString(R.string.wait_a_second)
                        optionsContent.text = getString(R.string.are_you_sure)
                        positiveOption.text = getString(R.string.yes)
                        negativeOption.text = getString(R.string.no)

                        positiveOption.setOnClickListener {
                            bottomShareSheet.dismiss()
                            val email = currentAccount.email
                            val userName = currentAccount.username
                            val note = currentAccount.note
                            if (userName.isEmpty() && note.isEmpty()) {
                                val shareNoteWithoutUserName =
                                    "${"Email: $email"}\n${"Password: $decryptedViewPassword"}"
                                val myIntent = Intent(Intent.ACTION_SEND)
                                myIntent.type = "text/plane"
                                myIntent.putExtra(Intent.EXTRA_TEXT, shareNoteWithoutUserName)
                                context?.startActivity(myIntent)
                            } else {
                                val shareNote =
                                    "${"Email: $email"}\n${"UserName: $userName"}\n${"Password: $decryptedViewPassword"}\n${"Note: $note"}"
                                val myIntent = Intent(Intent.ACTION_SEND)
                                myIntent.type = "text/plane"
                                myIntent.putExtra(Intent.EXTRA_TEXT, shareNote)
                                context?.startActivity(myIntent)
                            }

                        }
                        negativeOption.setOnClickListener {
                            bottomShareSheet.dismiss()

                        }
                    }
                    shareDialog.root.setBottomSheet(bottomShareSheet)

                }

                if (currentAccount.username.isNotEmpty()) {
                    accountUsername.visibility = View.VISIBLE
                }
                if (currentAccount.note.isNotEmpty()) {
                    accountNote.visibility = View.VISIBLE
                }

                // functions of buttons
                positiveOption.text = "Delete"
                positiveOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                negativeOption.text = "Cancel"
                negativeOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )

                positiveOption.setOnClickListener {
                    bottomSheet.dismiss()
                    lifecycleScope.launch {
                        deleteViewModel.deleteEntry(viewModel, currentAccount)
                    }
                    Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT)
                        .show()
                }
                negativeOption.setOnClickListener {
                    bottomSheet.dismiss()
                }

                when (currentAccount.company.toLowerCase().trim()) {

                    "paypal" -> accountLogo.setImageResource(R.drawable.paypal)
                    "instagram" -> accountLogo.setImageResource(R.drawable.instagram)
                    "facebook" -> accountLogo.setImageResource(R.drawable.facebook)
                    "linkedin" -> accountLogo.setImageResource(R.drawable.linkedin)
                    "snapchat" -> accountLogo.setImageResource(R.drawable.snapchat)
                    "youtube" -> accountLogo.setImageResource(R.drawable.youtube)
                    "dropbox" -> accountLogo.setImageResource(R.drawable.dropbox)
                    "twitter" -> accountLogo.setImageResource(R.drawable.twitter)
                    "google drive" -> accountLogo.setImageResource(R.drawable.drive)
                    "netflix" -> accountLogo.setImageResource(R.drawable.netflix_logo)
                    "amazon prime" -> accountLogo.setImageResource(R.drawable.amazon_logo)
                    "spotify" -> accountLogo.setImageResource(R.drawable.spotify)
                    "discord" -> accountLogo.setImageResource(R.drawable.discord)
                    "github" -> accountLogo.setImageResource(R.drawable.cl_github)
                    "gmail" -> accountLogo.setImageResource(R.drawable.gmail)
                    "paytm" -> accountLogo.setImageResource(R.drawable.cl_paytm)
                    "quora" -> accountLogo.setImageResource(R.drawable.cl_quora)
                    "reddit" -> accountLogo.setImageResource(R.drawable.cl_reddit)
                    "others" -> accountLogo.setImageResource(R.drawable.general_account)
                }
            }

            dialog.root.setBottomSheet(bottomSheet)

        }

        accountAdapter.notifyDataSetChanged()
        recyclerView = binding.recyclerView
        recyclerView.apply {
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



//        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
//        viewModel.getAccounts().observe(viewLifecycleOwner) { account ->
//
//
//            accountList.clear()
//            accountList.addAll(account)
//
//            accountAdapter.setData(account)
//            if (account.isEmpty()) {
//                binding.emptyList.visibility = View.VISIBLE
//            } else {
//                binding.emptyList.visibility = View.GONE
//            }
//
//        }

        // Moving to AddFragment
        binding.fab.setOnClickListener {
            goToAddFragment()
        }

        setFilter()
        clearText()

        return binding.root
    }

    private fun calculatePasswordScore(password: String): Int {
        var score = 0
        // Check for password length
        if (password.length >= 8) {
            score += 1
        }
        // Check for uppercase letters
        if (password.matches(Regex(".*[A-Z].*"))) {
            score += 1
        }
        // Check for lowercase letters
        if (password.matches(Regex(".*[a-z].*"))) {
            score += 1
        }
        // Check for digits
        if (password.matches(Regex(".*\\d.*"))) {
            score += 1
        }
        // Check for special characters
        if (password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))) {
            score += 1
        }
        // Check for consecutive characters
        if (!password.matches(Regex("(.)\\1{2,}"))) {
            score += 1
        }
        // Calculate the password score out of 9
        return ((score.toFloat() / 6.toFloat()) * 9).toInt()
    }

    private fun clearText() {
        binding.apply {
            clearText.setOnClickListener {
                Util.hideKeyboard(requireActivity())
                it.visibility = View.GONE
                etFilter.apply {
                    setText("")
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                    clearFocus()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setFilter() {
        binding.etFilter.apply {

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isNotEmpty()) {
                        filter(getBaseStringForFiltering(p0.toString().lowercase()))
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        binding.clearText.visibility = View.VISIBLE
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                        binding.clearText.visibility = View.GONE
                        accountAdapter.setData(accountList)
                    }
                }

            })
        }
    }

    private fun filter(search: String) {
        val filterList = mutableListOf<Account>()
        if (search.isNotEmpty()) {
            for (account in accountList) {
                if (getBaseStringForFiltering(account.company.lowercase()).contains(search)) {
                    filterList.add(account)
                }
            }
            accountAdapter.setData(filterList)
        } else {
            accountAdapter.setData(accountList)
        }
    }


    private fun goToAddFragment() {
        val fragment = AddFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment, fragment)?.addToBackStack("tag")?.commit()
    }


//    override fun onResume() {
//        super.onResume()
//        viewModel.readAllData.observe(viewLifecycleOwner) { account ->
//            accountAdapter.setData(account)
//            if (account.isEmpty()) {
//                binding.emptyList.visibility = View.VISIBLE
//            } else {
//                binding.emptyList.visibility = View.GONE
//            }
//
//        }
//    }
}