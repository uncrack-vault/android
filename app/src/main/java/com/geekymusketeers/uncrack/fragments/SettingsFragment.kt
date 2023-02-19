package com.geekymusketeers.uncrack.fragments

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.AboutusModalBinding
import com.geekymusketeers.uncrack.databinding.FragmentSettingsBinding
import com.geekymusketeers.uncrack.helper.Util
import com.geekymusketeers.uncrack.helper.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.helper.Util.Companion.setBottomSheet


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var pref: SharedPreferences
    private var cancellationSignal: CancellationSignal? = null
    private var isFingerPrintEnabled: Boolean = false


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        pref = requireContext().getSharedPreferences(
            "mypref",
            Context.MODE_PRIVATE
        )
        isFingerPrintEnabled = pref.getBoolean("switchState", false)
        Util.log("OnCreateState: $isFingerPrintEnabled")
        binding.FingerPrintSwitch.isChecked = isFingerPrintEnabled

        binding.feedback.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(requireContext().resources.getString(R.string.mailTo))
            startActivity(openURL)
        }

        binding.about.setOnClickListener {

            val dialog = AboutusModalBinding.inflate(layoutInflater)
            val bottomSheet = requireContext().createBottomSheet()
            dialog.apply {

                optionsContent.text =
                    "I see a lot of people are tried of memorizing all their passwords, me also sometimes forgot the login credentials. So, after thinking about this situations, this app is build, to help user's to store their passwords and other information in a secured and organized manner."
                optionsContent1.text =
                    "UnCrack is a offline password manager that securely stores all your login credentials and other important information, so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with UnCrack."
            }
            dialog.root.setBottomSheet(bottomSheet)

        }
        binding.FingerPrintSwitch.apply {

            setOnTouchListener { _, event ->
                event.actionMasked == MotionEvent.ACTION_MOVE
            }

            setOnClickListener {
                isFingerPrintEnabled = isFingerPrintEnabled.not()

                val editor: SharedPreferences.Editor = pref.edit()
                editor.putBoolean("switchState", isFingerPrintEnabled)
                editor.apply()
                Util.log("test123: ${isFingerPrintEnabled.not()}")
                if (isFingerPrintEnabled) {
                    val biometricPrompt = BiometricPrompt.Builder(requireContext())
                        .setTitle("Unlock UnCrack")
                        .setSubtitle("Input your Fingerprint to ensure it's you...")
                        .setDescription("Enter biometric credentials to proceed")
                        .setNegativeButton("Cancel", requireActivity().mainExecutor) { _, _ ->
                            Toast.makeText(
                                requireContext(),
                                "Fingerprint not registered",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.build()

                    biometricPrompt.authenticate(
                        getCancellationSignal(),
                        requireActivity().mainExecutor,
                        authenticationCallback
                    )
                }
            }

            return binding.root
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(
                requireContext(),
                "Authentication cancelled by the user",
                Toast.LENGTH_SHORT
            ).show()
        }
        return cancellationSignal as CancellationSignal
    }

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    requireContext(),
                    "Authentication error : $errString",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
            }
        }
}