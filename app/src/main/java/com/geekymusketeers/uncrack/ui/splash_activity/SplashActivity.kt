package com.geekymusketeers.uncrack.ui.splash_activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.biometrics.BiometricPrompt
import android.os.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.ActivitySplashBinding
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.ui.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private var cancellationSignal: CancellationSignal? = null
    private lateinit var biometricPrompt: BiometricPrompt

    @RequiresApi(Build.VERSION_CODES.P)
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
                }.build()

            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }else{
            goToHomeScreen()
        }

    }


    private fun goToHomeScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1700)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(
                this,
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
                Toast.makeText(this@SplashActivity, "Authentication error", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                goToHomeScreen()

            }
        }

}