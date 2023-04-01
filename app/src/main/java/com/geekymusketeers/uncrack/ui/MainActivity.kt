package com.geekymusketeers.uncrack.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.databinding.ActivityMainBinding
import com.geekymusketeers.uncrack.databinding.UpdatemodalBinding
import com.geekymusketeers.uncrack.ui.fragments.GeneratePasswordFragment
import com.geekymusketeers.uncrack.ui.fragments.settings.SettingsFragment
import com.geekymusketeers.uncrack.ui.fragments.account.HomeFragment
import com.geekymusketeers.uncrack.ui.fragments.card.CardFragment
import com.geekymusketeers.uncrack.util.Util
import com.geekymusketeers.uncrack.util.Util.Companion.createBottomSheet
import com.geekymusketeers.uncrack.util.Util.Companion.setBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
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

        checkForUpdate()
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

    private fun checkForUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        task = appUpdateManager.appUpdateInfo

        // Checking for Update
        task.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                askUserForUpdate()
            }
        }
    }

    private fun askUserForUpdate() {
        val dialog = UpdatemodalBinding.inflate(layoutInflater)
        val bottomSheet = createBottomSheet()
        dialog.apply {

            optionsHeading.text = getString(R.string.update_available)
            optionsContent.text = getString(R.string.would_you_like_to_update)
            positiveOption.text = getString(R.string.update)
            positiveOption.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.white
                )
            )

            negativeOption.text = getString(R.string.later)
            negativeOption.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black
                )
            )

            positiveOption.setOnClickListener {
                bottomSheet.dismiss()
                val i = Intent(Intent.ACTION_VIEW)
                val uri = Uri.parse(Util.PLAYSTORE_URL)
                i.data = uri
                startActivity(i)
            }
            negativeOption.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
        dialog.root.setBottomSheet(bottomSheet)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }
}