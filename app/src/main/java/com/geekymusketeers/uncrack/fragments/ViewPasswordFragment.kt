package com.geekymusketeers.uncrack.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.FragmentViewPasswordBinding


class ViewPasswordFragment : Fragment(R.layout.fragment_view_password) {

    private lateinit var binding: FragmentViewPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_password, container, false)
    }

}