package com.geekymusketeers.uncrack.ui.splash_activity


import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.geekymusketeers.uncrack.databinding.ActivitySplashBinding
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.ui.auth.MasterKeyActivity
import com.geekymusketeers.uncrack.viewModel.KeyViewModel
//import com.geekymusketeers.uncrack.viewModel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private var cancellationSignal: CancellationSignal? = null
    private lateinit var biometricPrompt: BiometricPrompt
//    private val keyViewModel by viewModels<KeyViewModel> { ViewModelFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Hides action bar
        supportActionBar?.hide()

        val pref = applicationContext.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        val isSwitchClicked = pref.getBoolean("switchState", false)
        Util.log("switchValue $isSwitchClicked")

        if (isSwitchClicked){
            biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Unlock UnCrack")
                .setSubtitle("Input your Fingerprint to ensure it's you...")
                .setDescription("Enter biometric credentials to proceed")
                .setNegativeButton("Cancel", this.mainExecutor) { _, _ ->
                    // Show Material Dialog
                    showDialog()
                }
                .build()
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)

        }else{
            handleMasterKey()
        }

    }

    private fun handleMasterKey() {
//        keyViewModel.getMasterKey().observe(this) {
//            when {
//                it.isEmpty() -> {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(1000)
//                        val intent = Intent(this@SplashActivity, MasterKeyActivity::class.java)
//                        intent.putExtra("flow","createMasterKey")
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//                else -> {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(1000)
//                        val intent = Intent(this@SplashActivity, MasterKeyActivity::class.java)
//                        intent.putExtra("flow","askForMasterKey")
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//            }
//        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Authentication Cancelled")
            .setMessage("Do you want to try again?")
            .setPositiveButton("Yes") { _, _ ->
                // Try authentication again
                biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
            }
            .setNegativeButton("No") { _, _ ->
                // Cancel authentication and go back to the previous screen
                finish()
            }
            .show()
    }


    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            showDialog()
        }
        return cancellationSignal as CancellationSignal
    }


    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                showDialog()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                handleMasterKey()

            }
        }

}