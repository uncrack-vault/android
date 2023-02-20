package com.geekymusketeers.uncrack.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.adapter.OnBoardingAdapter
import com.geekymusketeers.uncrack.databinding.ActivityOnboardingBinding
import com.geekymusketeers.uncrack.data.model.Onboarding
import com.geekymusketeers.uncrack.ui.auth.FingerPrintActivity
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        var slideList = mutableListOf(
            Onboarding(R.raw.onboarding1,"Easily Manage all your credentials"),
            Onboarding(R.raw.onboarding2,"Inbuilt Fingerprint Authentication to sure your credentials"),
            Onboarding(R.raw.onboarding3,"Generate random password according to your choice")
        )

        val adapter = OnBoardingAdapter(slideList)
        binding.slidePager.adapter = adapter

        binding.indicatorView
            .setSliderColor(R.color.colorSlideInAct, R.color.colorSlideAct)
            .setSliderWidth(resources.getDimension(R.dimen._8sdp))
            .setSliderHeight(resources.getDimension(R.dimen._8sdp))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            .setupWithViewPager(binding.slidePager)

        binding.slidePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.slidePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)


                if (position==0){
                    binding.btnNext.visibility = View.INVISIBLE
                }else if(position == 1){
                    binding.btnNext.visibility = View.INVISIBLE
                } else if (position==2){
                    binding.btnNext.text = getString(R.string.let_s_go)
                    binding.btnNext.visibility = View.VISIBLE

                    binding.btnNext.setOnClickListener {
                        Intent(this@OnboardingActivity, FingerPrintActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(it)
                            finish()
                        }
                    }

                }

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val prefs = "slide"
        val sharedPreferences = getSharedPreferences("slideprefs", Context.MODE_PRIVATE)
        if(!sharedPreferences.getBoolean(prefs,false)){
            val editor = sharedPreferences.edit()
            editor.putBoolean(prefs,true)
            editor.apply()
        }else {
            Intent(this@OnboardingActivity, FingerPrintActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}