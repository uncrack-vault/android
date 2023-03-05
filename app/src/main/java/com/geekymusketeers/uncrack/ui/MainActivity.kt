package com.geekymusketeers.uncrack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.ActivityMainBinding
import com.geekymusketeers.uncrack.ui.fragments.card.CardFragment
import com.geekymusketeers.uncrack.ui.fragments.GeneratePasswordFragment
import com.geekymusketeers.uncrack.ui.fragments.HomeFragment
import com.geekymusketeers.uncrack.ui.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())

        //Hides action bar
        supportActionBar?.hide()

        bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.cardFragment ->{
                    loadFragment(CardFragment())
                    true
                }
                R.id.passwordFragment -> {
                    loadFragment(GeneratePasswordFragment())
                    true
                }
                R.id.settingsFragment -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> {

                }
            }
            true
        }

    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }
}