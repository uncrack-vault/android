package com.geekymusketeers.uncrack.ui.fragments.settings


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.adapter.SettingsItemAdapter
import com.geekymusketeers.uncrack.domain.model.SettingsItem
import com.geekymusketeers.uncrack.databinding.AboutusModalBinding
import com.geekymusketeers.uncrack.databinding.FragmentSettingsBinding
import com.geekymusketeers.uncrack.ui.fragments.account.HomeFragment
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsItemList: ArrayList<SettingsItem>
    private lateinit var settingsItemAdapter: SettingsItemAdapter


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE

        handleOperations()
        return binding.root
    }

    private fun handleOperations() {
        setupSettingsOptions()
    }

    private fun setupSettingsOptions() {

        settingsItemList = ArrayList()
        settingsItemList.apply {
            add(SettingsItem(0, R.drawable.lock, "Secure UnCrack"))
            add(SettingsItem(1, R.drawable.feedback, "Request Features"))
            add(SettingsItem(2, R.drawable.share, "Share UnCrack"))
            add(SettingsItem(3, R.drawable.info, "About"))
            add(SettingsItem(4, R.drawable.rating, "Rate us on Google Play"))
        }
        settingsItemAdapter = SettingsItemAdapter(requireContext(), settingsItemList) {

            when(it) {
                0 -> {
                    transaction()
                }
                1 -> {
                    giveFeedback()
                }
                2 -> {
                    shareApp()
                }
                3 -> {
                    aboutApp()
                }
                4 -> {
                    rateTheApp()
                }
            }
        }
        binding.settingsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = settingsItemAdapter
        }

    }

    private fun editProfile() {

    }

    private fun transaction() {
        val frag = SecurityFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.addToBackStack(null)?.commit()
    }

    private fun rateTheApp() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Util.PLAYSTORE_URL)
        startActivity(intent)
    }

    private fun shareApp() {
        val send = Util.SEND_APP
        val b = BitmapFactory.decodeResource(resources, R.drawable.banner_uncrack)
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/jpeg"
        val bytes = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        share.putExtra(Intent.EXTRA_TEXT, send)
        val path = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            b,
            "Invite",
            null
        )
        val imageUri = Uri.parse(path)
        share.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(share, "Select"))
    }

    private fun giveFeedback() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(requireContext().resources.getString(R.string.mailTo))
        startActivity(openURL)
    }

    private fun aboutApp() {
        val dialog = AboutusModalBinding.inflate(layoutInflater)
        val bottomSheet = requireContext().createBottomSheet()
        dialog.apply {

            optionsContent.text = getString(R.string.about_app_text)
            optionsContent1.text = getString(R.string.about_app_text_second)

        }
        dialog.root.setBottomSheet(bottomSheet)
    }
}