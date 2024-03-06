package com.geekymusketeers.uncrack.ui.fragments.card

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.domain.model.Card
import com.geekymusketeers.uncrack.databinding.FragmentCardDetialsAddBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.CardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*


class CardDetialsAddFragment : Fragment() {

    private var _binding: FragmentCardDetialsAddBinding? = null
    private val binding get() = _binding!!
    private var isRemoveText = false
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar
    private var selectedCard: String? = null

    private lateinit var addCardViewModel: CardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardDetialsAddBinding.inflate(inflater, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        initialization()
        settingCardDetails()
        handleOperations()
        settingLayoutAccordingToCard()
        binding.back.setOnClickListener {
            handleBackButton()
        }


        // Account List
        val accounts = resources.getStringArray(R.array.cards)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_items, accounts)
        binding.cardType.setAdapter(arrayAdapter)

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

    private fun handleBackButton() {

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
                transaction()
            }
            negativeOption.setOnClickListener {
                bottomSheet.dismiss()

            }
        }
        dialog.root.setBottomSheet(bottomSheet)
    }

    private fun settingLayoutAccordingToCard() {
        binding.cardType.afterTextChanged {
            selectedCard = it
            when (it.lowercase(Locale.getDefault())) {
                "visa" -> {
                    binding.demoAddCard.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.visa_bg,
                        null
                    )
                    setImageOnAccountNameChange(R.drawable.ic_visa)
                }
                "mastercard" -> {
                    binding.demoAddCard.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.mastercard_bg,
                        null
                    )
                    setImageOnAccountNameChange(R.drawable.ic_mastercard)
                }
                "rupay" -> {
                    binding.demoAddCard.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rupay_bg,
                        null
                    )
                    setImageOnAccountNameChange(R.drawable.rupay_logo)
                }
                "american express" -> {
                    binding.demoAddCard.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.americanexpress_bg,
                        null
                    )
                    setImageOnAccountNameChange(R.drawable.amex_logo)
                }
            }
        }
    }

    private fun setImageOnAccountNameChange(cardImageID: Int) {
        binding.demoCardType.apply {
            setImageResource(cardImageID)
        }
        binding.demoAddCard.visibility = View.VISIBLE
        binding.remainingCardLayout.visibility = View.VISIBLE
    }

    private fun handleOperations() {

        buttonLayout.setOnClickListener {
            val cardNo = binding.cardNumber.text.toString()
            val cardName = binding.CardHolderName.text.toString()
            val cvv = binding.CVV.text.toString()
            if (cvv.isEmpty()) {
                binding.cardCVVHelperTV.text = getString(R.string.please_enter_the_cvv)
                binding.cardCVVHelperTV.visibility = View.VISIBLE
                validation()
                stopProgress()
                return@setOnClickListener
            } else if (cardNo.isEmpty()) {
                binding.cardNumberHelperTV.text = getString(R.string.please_enter_the_card_number)
                binding.cardNumberHelperTV.visibility = View.VISIBLE
                validation()
                stopProgress()
                return@setOnClickListener
            } else if (cardName.isEmpty()) {
                binding.cardNameHelperTV.text =
                    getString(R.string.please_enter_the_card_holder_name)
                binding.cardNameHelperTV.visibility = View.VISIBLE
                validation()
                stopProgress()
                return@setOnClickListener
            }
            binding.progressAnimation.progressParent.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000L)
                // Inserting CardDetails to Room DB
                insertDetails()
                transaction()
            }

        }
    }

    private fun transaction() {
        val frag = CardFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment, frag)?.commit()
    }

    private fun insertDetails() {
        val type = binding.cardType.text.toString()
        val no = binding.cardNumber.text.toString()
        val name = binding.CardHolderName.text.toString()
        val month = binding.expiryMonth.text.toString()
        val year = binding.expiryYear.text.toString()
        val cvv = binding.CVV.text.toString()

        // Encrypting the data's
        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedNo = encryption.encryptOrNull(no)
        val encryptedMonth = encryption.encryptOrNull(month)
        val encryptedYear = encryption.encryptOrNull(year)
        val encryptedCVV = encryption.encryptOrNull(cvv)

        val card = Card(0, type, encryptedNo, name, encryptedMonth, encryptedYear, encryptedCVV)
        addCardViewModel.addCard(card)

    }

    private fun stopProgress() {
        buttonText.visibility = View.VISIBLE
        buttonProgress.visibility = View.GONE

    }

    private fun showProgress() {
        buttonText.visibility = View.GONE
        buttonProgress.visibility = View.VISIBLE
    }

    private fun validation() {
        binding.apply {
            cardNumberHelperTV.text = getString(R.string.please_enter_the_card_number)
            cardNumberHelperTV.visibility = View.VISIBLE
            cardNameHelperTV.text = getString(R.string.please_enter_the_card_holder_name)
            cardNameHelperTV.visibility = View.VISIBLE
            cardCVVHelperTV.text = getString(R.string.please_enter_the_cvv)
            cardCVVHelperTV.visibility = View.VISIBLE
        }
    }

    @SuppressLint("ResourceType")
    private fun settingCardDetails() {

        // Setting Card Number to the CardView

        binding.cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString().length
                Log.d("PayView", " length : ${str}")
                when (str) {
                    in 0..3 -> {
                        binding.apply {
                            cardNumberOne.text = s.toString()
                            cardNumberTwo.text = ""
                            cardNumberThree.text = ""
                            cardNumberFour.text = ""
                        }
                    }

                    in 5..8 -> {
                        binding.apply {
                            cardNumberOne.text = s.toString().substring(0, 4)
                            cardNumberTwo.text = s.toString().substring(5, str)
                            cardNumberThree.text = ""
                            cardNumberFour.text = ""
                        }

                    }

                    in 10..13 -> {
                        binding.apply {
                            cardNumberOne.text = s.toString().substring(0, 4)
                            cardNumberTwo.text = s.toString().substring(5, 9)
                            cardNumberThree.text = s.toString().substring(10, str)
                            cardNumberFour.text = ""
                        }
                    }

                    in 15..19 -> {
                        binding.apply {
                            cardNumberOne.text = s.toString().substring(0, 4)
                            cardNumberTwo.text = s.toString().substring(5, 9)
                            cardNumberThree.text = s.toString().substring(10, 14)
                            cardNumberFour.text = s.toString().substring(14, str)
                        }

                    }

                    4, 9, 14 -> {

                        if (!isRemoveText)
                            s?.insert(s.toString().length, " ")
                        else
                            s?.delete(s.toString().length - 1, s.toString().length)
                    }
                }
                if (str == 19) {
                    binding.expiryMonth.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isRemoveText = start < s!!.length
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        // Setting Year to the CardView

        binding.expiryYear.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    demoExpiryYear.text = s.toString()
                    expiryMonth.error = null
                    expiryYear.error = null
                }
                if (s?.length == 4) {
                    binding.CardHolderName.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Setting Month to the CardView

        binding.expiryMonth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    demoExpiryMonth.text = s.toString()
                    expiryMonth.error = null
                    expiryYear.error = null
                }
                if (s?.length == 2) {
                    binding.expiryYear.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Setting Name to the CardView

        binding.CardHolderName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.demoCardHolderName.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        // Setting CVV to the CardView

        binding.CVV.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.demoCvv.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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

    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                handleBackButton()
                true
            } else false
        }
    }

    private fun initialization() {
        addCardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
        binding.apply {
            btnSaveCardDetails.apply {
                this@CardDetialsAddFragment.buttonLayout = this.progressButtonBg
                this@CardDetialsAddFragment.buttonText = this.buttonText
                this@CardDetialsAddFragment.buttonText.text = getString(R.string.save_details)
                this@CardDetialsAddFragment.buttonProgress = this.buttonProgress
            }
        }
    }

}


