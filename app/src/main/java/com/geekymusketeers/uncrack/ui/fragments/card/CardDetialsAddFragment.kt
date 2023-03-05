package com.geekymusketeers.uncrack.ui.fragments.card

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Card
import com.geekymusketeers.uncrack.databinding.FragmentAddBinding
import com.geekymusketeers.uncrack.databinding.FragmentCardBinding
import com.geekymusketeers.uncrack.databinding.FragmentCardDetialsAddBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.ui.fragments.HomeFragment
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.geekymusketeers.uncrack.viewModel.CardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class CardDetialsAddFragment : Fragment() {

    private var _binding: FragmentCardDetialsAddBinding? = null
    private val binding get() = _binding!!
    private var isRemoveText = false
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar
    private var selectedCard: String? =null

    private lateinit var addCardViewModel: CardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCardDetialsAddBinding.inflate(inflater, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        initialization()
        settingCardDetails()
        handleOperations()
        settingLayoutAccordingToCard()
        binding.backButton.setOnClickListener {
            handleBackButton()
        }


        // Account List
        val accounts = resources.getStringArray(R.array.cards)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.list_items,accounts)
        binding.cardType.setAdapter(arrayAdapter)
        return binding.root
    }

    private fun handleBackButton() {

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
                transaction()
            }
            negativeOption.setOnClickListener {
                bottomSheet.dismiss()

            }
        }
    }

    private fun settingLayoutAccordingToCard() {
        binding.cardType.afterTextChanged{
            selectedCard = it
            when(it.toLowerCase()){
                "visa" -> setImageOnAccountNameChange(R.drawable.ic_visa)
                "mastercard" -> setImageOnAccountNameChange(R.drawable.ic_mastercard)

            }
        }
    }

    private fun setImageOnAccountNameChange(cardImageID:Int) {
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
//            showProgress()
            if (cardNo.isEmpty() && cardName.isEmpty() && cvv.isEmpty()){
                validation()
                return@setOnClickListener
            }else if (cvv.isEmpty()){
                binding.cardCVVHelperTV.text = "Please Enter the CVV."
                binding.cardCVVHelperTV.visibility = View.VISIBLE
            }else if (cardNo.isEmpty()){
                binding.cardNumberHelperTV.text = "Please Enter the Card Number."
                binding.cardNumberHelperTV.visibility = View.VISIBLE
            }else if (cardName.isEmpty()){
                binding.cardNameHelperTV.text = "Please Enter the Card Holder Name."
                binding.cardNameHelperTV.visibility = View.VISIBLE
            }
            // Inserting CardDetails to Room DB
            insertDetails()
            transaction()
        }
    }

    private fun transaction() {
        val frag = CardFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }

    private fun insertDetails() {
        val type = binding.cardType.text.toString()
        val no = binding.cardNumber.text.toString()
        val name = binding.CardHolderName.text.toString()
        val month = binding.expiryMonth.text.toString()
        val year = binding.expiryYear.text.toString()
        val cvv = binding.CVV.text.toString()

        val card = Card(0,type,no,name,month,year,cvv)
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
            cardNumberHelperTV.text = "Please Enter the Card Number."
            cardNumberHelperTV.visibility = View.VISIBLE
            cardNameHelperTV.text = "Please Enter the Card Holder Name."
            cardNameHelperTV.visibility = View.VISIBLE
            cardCVVHelperTV.text = "Please Enter the CVV."
            cardCVVHelperTV.visibility = View.VISIBLE
        }
    }
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
//                initData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Log.d("PayView", "start: $start count: $count after: $after s:${s?.length}")
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
//                initData()
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
//                initData()
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
//              initData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        // Setting CVV to the CardView

//        binding.CVV.addTextChangedListener(object :TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//                tv_card_cv.text = s.toString()
//                initData()
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
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

    private fun initialization() {
        addCardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
        binding.apply {
            btnSaveCardDetails.apply {
                this@CardDetialsAddFragment.buttonLayout = this.progressButtonBg
                this@CardDetialsAddFragment.buttonText = this.buttonText
                this@CardDetialsAddFragment.buttonText.text = "Save"
                this@CardDetialsAddFragment.buttonProgress = this.buttonProgress
            }
        }
    }

}


