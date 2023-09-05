package com.geekymusketeers.uncrack.ui.fragments.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Card
import com.geekymusketeers.uncrack.databinding.FragmentCardDetailsViewBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class CardDetailsViewFragment : Fragment() {

    private var _binding: FragmentCardDetailsViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar
    private lateinit var currentCard : Card
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardDetailsViewBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        initialization()
        fetchData()
        binding.back.setOnClickListener {
            handleBackButton()
        }

        return binding.root
    }

    private fun showCard(cardType: String) {
        when(cardType.lowercase(Locale.ROOT).trim()){
            "visa" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.visa_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.ic_visa)
                }

            }
            "mastercard" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.mastercard_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.ic_mastercard)
                }

            }
            "rupay" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rupay_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.rupay_logo)
                }

            }
            "american express" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.americanexpress_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.amex_logo)
                }

            }
        }
    }

    private fun fetchData() {
        val cardType = arguments?.getString("cardType").toString()
        val cardNumber = arguments?.getString("cardNumber").toString()
        val cardHolderName = arguments?.getString("cardName").toString()
        val cardExpireMonth = arguments?.getString("month").toString()
        val cardExpireYear = arguments?.getString("year").toString()
        val cardCVV = arguments?.getString("cvv").toString()

        binding.apply {
            viewCardNumber.setText(cardNumber)
            demoCardNumber.text = cardNumber
            viewCardHolderName.setText(cardHolderName)
            demoCardHolderName.text = cardHolderName
            viewExpireMonth.setText(cardExpireMonth)
            demoExpiryMonth.text = cardExpireMonth
            viewExpireYear.setText(cardExpireYear)
            demoExpiryYear.text = cardExpireYear
            viewCvv.setText(cardCVV)
            demoCvv.text = cardCVV
        }
        showCard(cardType)
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

    private fun transaction() {
        val frag = CardFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment, frag)?.commit()
    }

    private fun initialization() {
        binding.apply {
            btnEdit.apply {
                this@CardDetailsViewFragment.buttonLayout = this.progressButtonBg
                this@CardDetailsViewFragment.buttonText = this.buttonText
                this@CardDetailsViewFragment.buttonText.text = getString(R.string.edit)
                this@CardDetailsViewFragment.buttonProgress = this.buttonProgress
            }

        }
    }

}