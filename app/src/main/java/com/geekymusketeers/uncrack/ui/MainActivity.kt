package com.geekymusketeers.uncrack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
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
                else -> {

                }
            }
            true
        }

//        bottomNavigationView = binding.bottomNav
//
//        val navController: NavController = findNavController(R.id.fragment)
//        val appBarConfiguration =
//            AppBarConfiguration(setOf(R.id.homeFragment, R.id.passwordFragment, R.id.settingsFragment))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        bottomNavigationView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.homeFragment -> showBottomNav(bottomNavigationView)
//                R.id.passwordFragment -> showBottomNav(bottomNavigationView)
//                R.id.settingsFragment -> showBottomNav(bottomNavigationView)
////                R.id.settings -> showBottomNav(bottomNavigationView)
////                else -> hideBottomNav(bottomNavigationView)
//            }
//        }
    }

//    private fun showBottomNav(bottomNavigationView: BottomNavigationView) {
//        bottomNavigationView.visibility = View.VISIBLE
//
//    }

//    private fun hideBottomNav(bottomNavigationView: BottomNavigationView) {
//        bottomNavigationView.visibility = View.GONE
//
//    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }
}