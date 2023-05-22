package com.geekymusketeers.uncrack.ui.auth.master_key

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Key
import com.geekymusketeers.uncrack.databinding.FragmentUpdateMasterKeyBinding
import com.geekymusketeers.uncrack.ui.fragments.settings.SecurityFragment
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.viewModel.KeyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UpdateMasterKeyFragment : Fragment() {

    private  var _binding : FragmentUpdateMasterKeyBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar
    private lateinit var updateKeyViewModel: KeyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateMasterKeyBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        initialization()
        clickHandlers()

        binding.back.setOnClickListener {
            val frag = SecurityFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }

        return binding.root
    }

    private fun clickHandlers() {
        buttonLayout.setOnClickListener {
            updateKeyViewModel.getMasterKey().observe(viewLifecycleOwner) {masterKey ->

                val oldMasterKey = binding.layoutOldMasterKey.editText?.text.toString()
                val updatedMasterKey = binding.layoutMasterKey.editText?.text.toString()
                val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
                val correctOldMasterKey = encryption.decryptOrNull(masterKey[0].password)
                showProgress()

                if (oldMasterKey.isEmpty() || oldMasterKey.isBlank()) {
                    binding.apply {
                        oldMasterKeyHelperTV.text = getString(R.string.old_master_key_cannot_be_blank)
                        oldMasterKeyHelperTV.visibility = View.VISIBLE
                    }
                    stopProgress()
                }
                else {
                    if (oldMasterKey!=correctOldMasterKey){
                        binding.apply {
                            oldMasterKeyHelperTV.text = getString(R.string.old_master_key_is_incorrect)
                            oldMasterKeyHelperTV.visibility = View.VISIBLE
                        }
                        stopProgress()
                    }
                    else {
                        if (updatedMasterKey.isBlank() || updatedMasterKey.isEmpty()) {
                            binding.apply {
                                updatedMasterKeyHelperTV.text = getString(R.string.master_key_cannot_be_blank)
                                updatedMasterKeyHelperTV.visibility = View.VISIBLE
                            }
                            stopProgress()
                        }
                        else {
                            if (updatedMasterKey.length <= 5) {
                                binding.apply {
                                    updatedMasterKeyHelperTV.text = getString(R.string.at_least_6_letter_long)
                                    updatedMasterKeyHelperTV.visibility = View.VISIBLE
                                }
                                stopProgress()
                            }
                            else {
                                lifecycleScope.launch(Dispatchers.Main) {
                                    delay(800L)
                                    updateKey()
                                    Toast.makeText(requireContext(),"Successfully Updated the Master Key",Toast.LENGTH_SHORT).show()
                                    goToSecurityFragment()
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun goToSecurityFragment() {
        val frag = SecurityFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }

    private fun updateKey() {

        val updatedKey = binding.layoutMasterKey.editText?.text.toString()

        val encrypt = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedUpdatedKey = encrypt.encryptOrNull(updatedKey)

        val updatedMasterKey = Key(0,encryptedUpdatedKey)
        updateKeyViewModel.setMasterKey(updatedMasterKey)
    }

    private fun showProgress() {
        buttonText.visibility = View.GONE
        buttonProgress.visibility = View.VISIBLE
    }
    private fun stopProgress() {
        buttonText.visibility = View.VISIBLE
        buttonProgress.visibility = View.GONE

    }

    private fun initialization() {
        updateKeyViewModel = ViewModelProvider(this)[KeyViewModel::class.java]
        binding.apply {
            btnUpdateMasterKey.apply {
                this@UpdateMasterKeyFragment.buttonLayout = this.progressButtonBg
                this@UpdateMasterKeyFragment.buttonText = this.buttonText
                this@UpdateMasterKeyFragment.buttonText.text = getString(R.string.update)
                this@UpdateMasterKeyFragment.buttonProgress = this.buttonProgress
            }
        }
    }

}