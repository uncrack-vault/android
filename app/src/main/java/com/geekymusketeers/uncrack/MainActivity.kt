package com.geekymusketeers.uncrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav)
//        bottomNav.setupWithNavController(navController)
    }
}