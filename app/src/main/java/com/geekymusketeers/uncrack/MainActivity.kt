package com.geekymusketeers.uncrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.geekymusketeers.uncrack.databinding.ActivityMainBinding
import com.geekymusketeers.uncrack.fragments.GeneratePasswordFragment
import com.geekymusketeers.uncrack.fragments.HomeFragment
import com.geekymusketeers.uncrack.fragments.SettingsFragment
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