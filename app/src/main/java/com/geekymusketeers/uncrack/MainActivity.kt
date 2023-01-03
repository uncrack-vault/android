package com.geekymusketeers.uncrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.geekymusketeers.uncrack.databinding.ActivityMainBinding
import com.geekymusketeers.uncrack.fragments.GeneratePasswordFragment
import com.geekymusketeers.uncrack.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        //Hides action bar
        supportActionBar?.hide()

        binding.bottomNav.setOnItemSelectedListener {

            when(it.groupId){
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.passwordFragment -> replaceFragment(GeneratePasswordFragment())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment,fragment)
        fragmentTransition.commit()
    }
}