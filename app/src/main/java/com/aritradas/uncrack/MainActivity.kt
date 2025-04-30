package com.aritradas.uncrack

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialManager.Companion.create
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
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity(), InstallStateUpdatedListener {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val viewModel: SharedViewModel by viewModels()

    @Inject
    lateinit var appBioMetricManager: AppBioMetricManager

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var credentialManager: CredentialManager

    private val UPDATE_REQUEST_CODE = 500

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            Log.d("AppUpdate", "Update flow failed! Result code: ${result.resultCode}")
            // User rejected or update failed
            if (BuildConfig.DEBUG) {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: ${result.resultCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.d("AppUpdate", "Update flow succeeded")
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

        // Initialize the AppUpdateManager
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager.registerListener(this)
        credentialManager = create(this)

        settingsViewModel.isScreenshotEnabled.observe(this) { isEnabled ->
            if (isEnabled) {
                window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
            } else {
                window.setFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE,
                    android.view.WindowManager.LayoutParams.FLAG_SECURE)
            }
        }

        // Check for app updates
        checkForAppUpdate()

        setContent {
            UnCrackTheme {
                val connectivityObserver = NetworkConnectivityObserver(applicationContext)
                Navigation(connectivityObserver, credentialManager)
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
        Log.d("AppUpdate", "Checking for app updates...")
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            Log.d("AppUpdate", "Update availability: ${appUpdateInfo.updateAvailability()}")

            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    Log.d(
                        "AppUpdate",
                        "Update available! Version code: ${appUpdateInfo.availableVersionCode()}"
                    )

                    // Check update priority if available
                    val updatePriority = try {
                        appUpdateInfo.updatePriority()
                    } catch (e: Exception) {
                        0
                    }

                    // For high priority updates (4-5), use immediate update if possible
                    if (updatePriority >= 4 && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Log.d("AppUpdate", "Starting IMMEDIATE update flow (high priority)")
                        startImmediateUpdate(appUpdateInfo)
                    }
                    // For normal updates, prefer flexible but fall back to immediate if needed
                    else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        Log.d("AppUpdate", "Starting FLEXIBLE update flow")
                        startFlexibleUpdate(appUpdateInfo)
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Log.d("AppUpdate", "Starting IMMEDIATE update flow")
                        startImmediateUpdate(appUpdateInfo)
                    } else {
                        Log.d("AppUpdate", "No supported update types allowed")
                    }
                }

                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    Log.d("AppUpdate", "Update already in progress")
                    // Resume the update if it was already started
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        startImmediateUpdate(appUpdateInfo)
                    }
                }

                else -> {
                    Log.d(
                        "AppUpdate",
                        "No update available or unknown state: ${appUpdateInfo.updateAvailability()}"
                    )
                    // Removed the Toast message that showed "No Update Available"
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("AppUpdate", "Failed to check for updates", exception)
        }
    }

    private fun startImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
            )
        } catch (e: Exception) {
            Log.e("AppUpdate", "Error starting immediate update flow", e)
        }
    }

    private fun startFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
            )
        } catch (e: Exception) {
            Log.e("AppUpdate", "Error starting flexible update flow", e)
        }
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app
            popupSnackbarForCompleteUpdate()
        }

        Log.d("AppUpdate", "Install state updated: ${state.installStatus()}")
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "An update has been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("INSTALL") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    override fun onResume() {
        super.onResume()

        // For FLEXIBLE updates, check if an update has been downloaded
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            // If an update is downloaded but not installed,
            // notify the user to complete the update
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("AppUpdate", "Update downloaded but not installed")
                popupSnackbarForCompleteUpdate()
            }

            // Check if an immediate update is in progress and was interrupted
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                Log.d("AppUpdate", "Update in progress - resuming")
                // If an in-app update is already running, resume the update
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(appUpdateInfo.updatePriority()).build()
                    )
                } catch (e: Exception) {
                    Log.e("AppUpdate", "Error resuming update", e)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the listener to prevent memory leaks
        appUpdateManager.unregisterListener(this)
    }
}