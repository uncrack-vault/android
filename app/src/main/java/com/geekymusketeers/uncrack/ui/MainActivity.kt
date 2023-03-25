package com.geekymusketeers.uncrack.ui


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.ActivityMainBinding
import com.geekymusketeers.uncrack.ui.fragments.GeneratePasswordFragment
import com.geekymusketeers.uncrack.ui.fragments.SettingsFragment
import com.geekymusketeers.uncrack.ui.fragments.account.HomeFragment
import com.geekymusketeers.uncrack.ui.fragments.card.CardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav: BottomNavigationView
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var task: Task<AppUpdateInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        //Hides action bar
        supportActionBar?.hide()

        appUpdateManager = AppUpdateManagerFactory.create(this)
        task = appUpdateManager.appUpdateInfo

        // Checking for Update
        task.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Update Alert!")
                    .setMessage("Kindly update your app.")
                    .setPositiveButton("Update") { _, _ ->

                        val i = Intent(Intent.ACTION_VIEW)
                        val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.geekymusketeers.uncrack")
                        i.data = uri
                        startActivity(i)

                    }
                    .setNegativeButton("No") { _, _ ->
                        finish()
                    }
                    .show()
            }
        }



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