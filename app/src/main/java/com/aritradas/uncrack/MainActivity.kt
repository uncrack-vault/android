package com.aritradas.uncrack

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aritradas.uncrack.navigation.Navigation
import com.aritradas.uncrack.presentation.settings.SettingsViewModel
import com.aritradas.uncrack.sharedViewModel.SharedViewModel
import com.aritradas.uncrack.ui.theme.UnCrackTheme
import com.aritradas.uncrack.util.AppBioMetricManager
import com.aritradas.uncrack.util.NetworkConnectivityObserver
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val viewModel: SharedViewModel by viewModels()

    @Inject
    lateinit var appBioMetricManager: AppBioMetricManager

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            Toast.makeText(
                applicationContext,
                "Update flow failed! Result code : ${result.resultCode}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            )
        )

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        settingsViewModel.isScreenshotEnabled.observe(this) { isEnabled ->
            if (isEnabled) {
                window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
            } else {
                window.setFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE,
                    android.view.WindowManager.LayoutParams.FLAG_SECURE)
            }
        }

        checkForAppUpdate()

        setContent {
            UnCrackTheme {
                val connectivityObserver = NetworkConnectivityObserver(applicationContext)
                Navigation(this, connectivityObserver)
            }
        }

        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.initAuth.collect { value ->
                    if (value && viewModel.loading.value) {
                        viewModel.showBiometricPrompt(this@MainActivity)
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.finishActivity.collect { value ->
                    if (value) finish()
                }
            }
        }
    }

    private fun checkForAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    activityResultLauncher,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            } else {
                Toast.makeText(
                    applicationContext,
                    "No Update Available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
