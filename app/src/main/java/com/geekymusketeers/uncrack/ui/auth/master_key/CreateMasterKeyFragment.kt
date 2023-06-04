package com.geekymusketeers.uncrack.ui.auth.master_key

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.data.model.Key
import com.geekymusketeers.uncrack.databinding.FragmentCreateMasterKeyBinding
import com.geekymusketeers.uncrack.ui.MainActivity
import com.geekymusketeers.uncrack.util.Encryption
import com.geekymusketeers.uncrack.viewModel.KeyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CreateMasterKeyFragment : Fragment() {

    private  var _binding : FragmentCreateMasterKeyBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonLayout: ConstraintLayout
    private lateinit var buttonText: TextView
    private lateinit var buttonProgress: ProgressBar
    private lateinit var keyViewModel: KeyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMasterKeyBinding.inflate(inflater,container,false)

        initialization()
        clickHandlers()
        return binding.root
    }

    private fun clickHandlers() {
        buttonLayout.setOnClickListener {
            val masterKey = binding.masterKey.text.toString()
            val confirmMasterKey = binding.confirmMasterKey.text.toString()
            showProgress()
            if (masterKey.isEmpty() || masterKey.isBlank()) {
                binding.apply {
                    masterKeyHelperTV.text = getString(R.string.master_key_cannot_be_blank)
                    masterKeyHelperTV.visibility = View.VISIBLE
                }
                stopProgress()
                return@setOnClickListener
            }
            else if (masterKey.length <= 5) {
                binding.apply {
                    masterKeyHelperTV.text = getString(R.string.at_least_6_letter_long)
                    masterKeyHelperTV.visibility = View.VISIBLE
                }
                stopProgress()
                return@setOnClickListener
            }
            if (confirmMasterKey.isEmpty() || confirmMasterKey.isBlank()) {
                binding.apply {
                    confirmMasterKeyHelperTV.text = getString(R.string.confirm_master_key_cannot_be_blank)
                    confirmMasterKeyHelperTV.visibility = View.VISIBLE
                }
                stopProgress()
                return@setOnClickListener
            }
            else if (masterKey != confirmMasterKey) {
                binding.apply {
                    confirmMasterKeyHelperTV.text = getString(R.string.enter_correct_master_key)
                    confirmMasterKeyHelperTV.visibility = View.VISIBLE
                }
                stopProgress()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.Main) {
                delay(1400L)
                setMasterKey()
                goToMainActivity()
            }

        }
    }

    private fun goToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setMasterKey() {
        val masterKey = binding.masterKey.text.toString()

        // Encrypting the master key
        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedKey = encryption.encryptOrNull(masterKey)

        val key = Key(0,encryptedKey)
        keyViewModel.setMasterKey(key)
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
        keyViewModel = ViewModelProvider(this)[KeyViewModel::class.java]
        binding.apply {
            btnSaveMasterKey.apply {
                this@CreateMasterKeyFragment.buttonLayout = this.progressButtonBg
                this@CreateMasterKeyFragment.buttonText = this.buttonText
                this@CreateMasterKeyFragment.buttonText.text = getString(R.string.save_master_key)
                this@CreateMasterKeyFragment.buttonProgress = this.buttonProgress
            }
        }
    }

}