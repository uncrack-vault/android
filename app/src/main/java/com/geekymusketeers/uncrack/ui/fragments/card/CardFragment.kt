package com.geekymusketeers.uncrack.ui.fragments.card

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.adapter.CardAdapter
import com.geekymusketeers.uncrack.databinding.FragmentCardBinding
import com.geekymusketeers.uncrack.databinding.ViewcardmodalBinding
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.CardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardViewModel: CardViewModel
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardRecyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
        cardAdapter = CardAdapter(requireContext()){ currentCard ->

            val dialog = ViewcardmodalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()

            dialog.apply {

                val decry = Encryption.getDefault("Key", "Salt", ByteArray(16))
                val decryNumber = decry.decryptOrNull(currentCard.cardNumber)
                val decryMonth = decry.decryptOrNull(currentCard.expirationMonth)
                val decryYear = decry.decryptOrNull(currentCard.expirationYear)
                val decryCVV = decry.decryptOrNull(currentCard.cvv)

                cardNumber.text = decryNumber
                expiryMonth.text = decryMonth
                expiryYear.text = decryYear
                demoCardName.text = currentCard.cardHolderName
                cvv.text = decryCVV

                deleteOption.text = "Remove Card"
                deleteOption.setTextColor(
                    ContextCompat.getColor(requireContext(),R.color.white)
                )

                deleteOption.setOnClickListener {
                    bottomSheet.dismiss()
                    cardViewModel.deleteCard(currentCard)
                    Toast.makeText(requireContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show()
                }

                when(currentCard.cardType.toLowerCase().trim()){
                    "visa" -> cardType.setImageResource(R.drawable.ic_visa)
                    "mastercard" -> {
                        viewCard.backgroundTintList = ColorStateList.valueOf(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.mastercard,
                                null
                            )
                        )
                        cardType.setImageResource(R.drawable.ic_mastercard)
                    }
                }
            }
            dialog.root.setBottomSheet(bottomSheet)
        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE

        // Setting up RecyclerView
        cardAdapter.notifyDataSetChanged()
        cardRecyclerView = binding.cardRecyclerView
        cardRecyclerView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        cardViewModel.readAllCardData.observe(viewLifecycleOwner) { card ->
            cardAdapter.setCardData(card)
            if (card.isEmpty()) {
                binding.emptyCardList.visibility = View.VISIBLE
            } else {
                binding.emptyCardList.visibility = View.GONE
            }
        }
        // Moving to AddCardDetailsFragment
        binding.cardFab.setOnClickListener {
            goToCardAddFragment()
        }
        binding.cardFabCircle.setOnClickListener {
            goToCardAddFragment()
        }

        setUpFab()
        return binding.root

    }

    private fun setUpFab() {
        binding.cardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabText.visibility == View.VISIBLE) {
                    binding.fabText.visibility = View.GONE
                } else if (dy < 0 && binding.fabText.visibility != View.VISIBLE) {
                    binding.fabText.visibility = View.VISIBLE
                }
            }
        })
    }

//    override fun onResume() {
//        super.onResume()
//        cardViewModel.readAllCardData.observe(viewLifecycleOwner) { card ->
//            cardAdapter.setCardData(card)
//            if (card.isEmpty()) {
//                binding.emptyCardList.visibility = View.VISIBLE
//            } else {
//                binding.emptyCardList.visibility = View.GONE
//            }
//
//        }
//    }

    private fun goToCardAddFragment() {
        val fragment = CardDetialsAddFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment,fragment)?.addToBackStack( "tag" )?.commit()
    }
}