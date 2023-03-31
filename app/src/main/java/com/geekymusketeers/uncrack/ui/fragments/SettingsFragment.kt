package com.geekymusketeers.uncrack.ui.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.AboutusModalBinding
import com.geekymusketeers.uncrack.databinding.FragmentSettingsBinding
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import java.io.ByteArrayOutputStream
import java.util.*


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
            feedback()
        }

        binding.about.setOnClickListener {
            about()
        }

        binding.shareApp.setOnClickListener {
            shareApp()
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


    private fun shareApp() {
        val send = "Checkout the app on Play Store \n https://play.google.com/store/apps/details?id=com.geekymusketeers.uncrack"
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

    private fun about() {
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

    private fun feedback() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(requireContext().resources.getString(R.string.mailTo))
        startActivity(openURL)
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