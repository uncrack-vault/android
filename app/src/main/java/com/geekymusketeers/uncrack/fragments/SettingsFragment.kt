package com.geekymusketeers.uncrack.fragments

import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.geekymusketeers.uncrack.databinding.AboutusModalBinding
import com.geekymusketeers.uncrack.databinding.FragmentSettingsBinding
import com.geekymusketeers.uncrack.databinding.OptionsModalBinding
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet
import com.geekymusketeers.uncrack.ui.MainActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var cancellationSignal : CancellationSignal? = null
    private val authenticationCallback : BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Authentication error : $errString", Toast.LENGTH_SHORT).show()
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)

            }
        }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)

        binding.about.setOnClickListener {

            val dialog = AboutusModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()
            dialog.apply {

                optionsContent.text = "I see a lot of people are tried of memorizing all their passwords, me also sometimes forgot the login credentials. So, after thinking about this situations, this app is build, to help user's to store their passwords and other information in a secured and organized manner."
                optionsContent1.text = "UnCrack is a offline password manager that securely stores all your login credentials and other important information, so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with UnCrack."
            }
            dialog.root.setBottomSheet(bottomSheet)

        }

        binding.FingerPrintSwitch.setOnClickListener {

            if (binding.FingerPrintSwitch.isChecked){

                val biometricPrompt = BiometricPrompt.Builder(requireContext())
                    .setTitle("Unlock UnCrack")
                    .setSubtitle("Input your Fingerprint to ensure it's you...")
                    .setDescription("Enter biometric credentials to proceed")
                    .setNegativeButton("Cancel",requireActivity().mainExecutor) { _, _ ->
                        Toast.makeText(requireContext(), "Fingerprint not saved", Toast.LENGTH_SHORT).show()
                    }.build()

                biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor, authenticationCallback)
            }

        }


        return binding.root
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(requireContext(), "Authentication cancelled by the user", Toast.LENGTH_SHORT).show()
        }
        return cancellationSignal as CancellationSignal

    }


}