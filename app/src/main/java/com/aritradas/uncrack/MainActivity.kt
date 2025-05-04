package com.aritradas.uncrack

import android.os.Build
import android.os.Bundle
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
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity(), InstallStateUpdatedListener {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val viewModel: SharedViewModel by viewModels()

    @Inject
    lateinit var appBioMetricManager: AppBioMetricManager

    private lateinit var appUpdateManager: AppUpdateManager

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            Timber.d("Update flow failed! Result code: ${result.resultCode}")
            if (BuildConfig.DEBUG) {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: ${result.resultCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Timber.d("Update flow succeeded")
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
        Timber.d("Checking for app updates...")
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            Timber.d("Update availability: ${appUpdateInfo.updateAvailability()}")

            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    Timber.d("Update available! Version code: ${appUpdateInfo.availableVersionCode()}")

                    // Check update priority if available
                    val updatePriority = try {
                        appUpdateInfo.updatePriority()
                    } catch (_: Exception) {
                        0
                    }

                    // For high priority updates (4-5), use immediate update if possible
                    if (updatePriority >= 4 && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Timber.d("Starting IMMEDIATE update flow (high priority)")
                        startImmediateUpdate(appUpdateInfo)
                    }
                    // For normal updates, prefer flexible but fall back to immediate if needed
                    else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        Timber.d("Starting FLEXIBLE update flow")
                        startFlexibleUpdate(appUpdateInfo)
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Timber.d("Starting IMMEDIATE update flow")
                        startImmediateUpdate(appUpdateInfo)
                    } else {
                        Timber.d("No supported update types allowed")
                    }
                }

                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    Timber.d("Update already in progress")
                    // Resume the update if it was already started
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        startImmediateUpdate(appUpdateInfo)
                    }
                }

                else -> {
                    Timber.d("No update available or unknown state: ${appUpdateInfo.updateAvailability()}")
                    // Removed the Toast message that showed "No Update Available"
                }
            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to check for updates")
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
            Timber.e(e, "Error starting immediate update flow")
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
            Timber.e(e, "Error starting flexible update flow")
        }
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app
            popupSnackbarForCompleteUpdate()
        }

        Timber.d("Install state updated: ${state.installStatus()}")
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
                Timber.d("Update downloaded but not installed")
                popupSnackbarForCompleteUpdate()
            }

            // Check if an immediate update is in progress and was interrupted
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                Timber.d("Update in progress - resuming")
                // If an in-app update is already running, resume the update
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(appUpdateInfo.updatePriority()).build()
                    )
                } catch (e: Exception) {
                    Timber.e(e, "Error resuming update")
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