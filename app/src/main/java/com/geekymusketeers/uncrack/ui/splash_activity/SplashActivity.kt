package com.geekymusketeers.uncrack.ui.splash_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.geekymusketeers.uncrack.databinding.ActivitySplashBinding
import com.geekymusketeers.uncrack.ui.MainActivity
import com.geekymusketeers.uncrack.ui.auth.FingerPrintActivity
import com.geekymusketeers.uncrack.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hides action bar
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}