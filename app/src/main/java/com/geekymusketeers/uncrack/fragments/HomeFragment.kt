package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.adapter.AccountAdapter
import com.geekymusketeers.uncrack.databinding.FragmentHomeBinding
import com.geekymusketeers.uncrack.viewModel.AccountViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val adapter = AccountAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { account ->
            adapter.setData(account)
        })

        // Moving to AddFragment
        binding.floatingActionButton.setOnClickListener { view ->
            val fragment = AddFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment,fragment)?.commit()
        }

        return binding.root
    }


}