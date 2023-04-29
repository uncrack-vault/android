package com.geekymusketeers.uncrack.ui.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.FragmentSecurityBinding
import com.geekymusketeers.uncrack.databinding.FragmentSettingsBinding
import com.geekymusketeers.uncrack.ui.auth.fragment.UpdateMasterKeyFragment
import com.geekymusketeers.uncrack.ui.fragments.account.HomeFragment
import com.geekymusketeers.uncrack.util.Util


class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!
    lateinit var pref: SharedPreferences
    private var cancellationSignal: CancellationSignal? = null
    private var isFingerPrintEnabled: Boolean = false

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecurityBinding.inflate(inflater, container, false)

        pref = requireContext().getSharedPreferences(
            "mypref",
            Context.MODE_PRIVATE
        )
        isFingerPrintEnabled = pref.getBoolean("switchState", false)
        Util.log("OnCreateState: $isFingerPrintEnabled")
        binding.FingerPrintSwitch.isChecked = isFingerPrintEnabled

        binding.updateMasterKeyLayout.setOnClickListener {
            transaction()
        }

        binding.back.setOnClickListener {
            val frag = SettingsFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
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
                        .setTitle("Secure UnCrack")
                        .setSubtitle("Give your Fingerprint to ensure it's security")
                        .setDescription("Enter your fingerprint to proceed")
                        .setNegativeButton("Cancel", requireActivity().mainExecutor) { _, _ ->
                            Toast.makeText(
                                requireContext(),
                                "Fingerprint not given",
                                Toast.LENGTH_SHORT
                            ).show()
                            isFingerPrintEnabled = false // turn off switch
                            binding.FingerPrintSwitch.isChecked = false // update switch state
                        }.build()

                    biometricPrompt.authenticate(
                        getCancellationSignal(),
                        requireActivity().mainExecutor,
                        authenticationCallback
                    )
                } else {
                    binding.FingerPrintSwitch.isChecked = false // update switch state
                }
            }
            return binding.root
        }
    }

    private fun transaction() {
        val frag = UpdateMasterKeyFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
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
                    "Authentication error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
            }
        }
}