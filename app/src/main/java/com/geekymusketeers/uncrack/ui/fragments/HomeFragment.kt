package com.geekymusketeers.uncrack.ui.fragments

import android.annotation.SuppressLint
import com.geekymusketeers.uncrack.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.uncrack.adapter.AccountAdapter
import com.geekymusketeers.uncrack.data.model.Account
import com.geekymusketeers.uncrack.data.room.AccountDao
import com.geekymusketeers.uncrack.databinding.FragmentHomeBinding
import com.geekymusketeers.uncrack.databinding.SharepasswordModalBinding
import com.geekymusketeers.uncrack.databinding.ViewpasswordModalBinding
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.getBaseStringForFiltering
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.viewModel.AccountViewModel
import com.geekymusketeers.uncrack.viewModel.AddEditViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel
    private lateinit var deleteViewModel: AddEditViewModel
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var recyclerView: RecyclerView
    private var accountList = mutableListOf<Account>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
        
        deleteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[AddEditViewModel::class.java]



        accountAdapter = AccountAdapter(requireContext()){ currentAccount ->

            val dialog = ViewpasswordModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()

            dialog.apply {

                // Fetching data and setting it to textview and edittext
                accountName.text = currentAccount.company
                accountEmail.text = currentAccount.email
                accountUsername.text = "UserName:  " + currentAccount.username
                accountPassword.setText(currentAccount.password)

                shareBtn.setOnClickListener {

                    val shareDialog = SharepasswordModalBinding.inflate(layoutInflater)
                    val bottomSheet = requireContext().createBottomSheet()

                    shareDialog.apply {
                        optionsHeading.text = "Wait a Second"
                        optionsContent.text = "Are you sure you want to share your credentials with other's?"
                        positiveOption.text = "Yes"
                        negativeOption.text = "No"

                        positiveOption.setOnClickListener {
                            bottomSheet.dismiss()
                            val email = currentAccount.email
                            val userName = currentAccount.username
                            val password = currentAccount.password
                            if (userName.isEmpty()){
                                val shareNoteWithoutUserName = "${"Email: $email"}\n${"Password: $password"}"
                                val myIntent= Intent(Intent.ACTION_SEND)
                                myIntent.type = "text/plane"
                                myIntent.putExtra(Intent.EXTRA_TEXT,shareNoteWithoutUserName)
                                context?.startActivity(myIntent)
                            }else{
                                val shareNote = "${"Email: $email"}\n${"UserName: $userName"}\n${"Password: $password"}"
                                val myIntent= Intent(Intent.ACTION_SEND)
                                myIntent.type = "text/plane"
                                myIntent.putExtra(Intent.EXTRA_TEXT,shareNote)
                                context?.startActivity(myIntent)
                            }

                        }
                        negativeOption.setOnClickListener {
                            bottomSheet.dismiss()

                        }
                    }
                    shareDialog.root.setBottomSheet(bottomSheet)

                }

                if(currentAccount.username.isNotEmpty()) {
                    accountUsername.visibility = View.VISIBLE
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
                        deleteViewModel.deleteEntry(viewModel,currentAccount)
                    }
                    Snackbar.make(binding.root, "Successfully Deleted", Snackbar.LENGTH_SHORT).show()
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



        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { account ->


            accountList.clear()
            accountList.addAll(account)

            accountAdapter.setData(account)
            if (account.isEmpty()){
                binding.emptyList.visibility = View.VISIBLE
            }else{
                binding.emptyList.visibility = View.GONE
            }

        })

        // Moving to AddFragment
        binding.fab.setOnClickListener {
            goToAddFragment()
        }

        binding.fabCircle.setOnClickListener {
            goToAddFragment()
        }

        setFilter()

        setUpFab()
//        popup_menu()
        return binding.root
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
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clear_text, 0)
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                        filter("")
                    }
                }

            })

            setOnTouchListener(View.OnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                        this.setText("")
                        return@OnTouchListener true
                    }
                }
                return@OnTouchListener false
            })

        }
    }

    private fun filter(search: String) {
        val filterList = mutableListOf<Account>()
        if (search.isNotEmpty()){
            for (account in accountList){
                if (getBaseStringForFiltering(account.company.lowercase()).contains(search)){
                    filterList.add(account)
                }
            }
            accountAdapter.setData(filterList)
        }else{
            accountAdapter.setData(accountList)
        }
    }


    private fun goToAddFragment() {
        val fragment = AddFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment,fragment)?.addToBackStack( "tag" )?.commit()
    }


    override fun onResume() {
        super.onResume()
            viewModel.readAllData.observe(viewLifecycleOwner, Observer { account ->
                accountAdapter.setData(account)
                if (account.isEmpty()){
                    binding.emptyList.visibility = View.VISIBLE
                }else{
                    binding.emptyList.visibility = View.GONE
                }

            })
    }

    private fun setUpFab() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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


}