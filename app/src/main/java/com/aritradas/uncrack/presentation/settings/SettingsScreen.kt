package com.aritradas.uncrack.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritradas.uncrack.MainActivity
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.SettingsItemGroup
import com.aritradas.uncrack.components.ThemeDialog
import com.aritradas.uncrack.components.UCSettingsCard
import com.aritradas.uncrack.components.UCSwitchCard
import com.aritradas.uncrack.components.UCTopAppBar
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.OnPrimaryContainerLight
import com.aritradas.uncrack.ui.theme.OnSurfaceVariantLight
import com.aritradas.uncrack.ui.theme.SurfaceVariantLight
import com.aritradas.uncrack.ui.theme.bold20
import com.aritradas.uncrack.ui.theme.medium14
import com.aritradas.uncrack.ui.theme.normal16
import com.aritradas.uncrack.ui.theme.semiBold18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val isScreenshotEnabled by settingsViewModel.isScreenshotEnabled.observeAsState(false)
    val onLogOutComplete by settingsViewModel.onLogOutComplete.observeAsState(false)
    val onDeleteAccountComplete by settingsViewModel.onDeleteAccountComplete.observeAsState(false)
    val biometricAuthState by settingsViewModel.biometricAuthState.collectAsState()
    var openThemeDialog by remember { mutableStateOf(false) }
    var openLogoutDialog by remember { mutableStateOf(false) }
    var openDeleteAccountDialog by remember { mutableStateOf(false) }

    if (onLogOutComplete || onDeleteAccountComplete) {
        navController.navigate(Screen.LoginScreen.name)
    }

    when {
        openThemeDialog -> {
            ThemeDialog(onDismissRequest = { openThemeDialog = false })
        }

        openLogoutDialog -> {
            AlertDialog(
                onDismissRequest = { openLogoutDialog = false },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = null,
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.logout),
                        style = normal16.copy(color = OnPrimaryContainerLight)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.are_you_sure_you_want_to_logout),
                        style = normal16.copy(color = OnSurfaceVariantLight)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            settingsViewModel.logout()
                            openLogoutDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            "Logout",
                            style = medium14
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openLogoutDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = medium14
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            )
        }

        openDeleteAccountDialog -> {
            AlertDialog(
                onDismissRequest = { openDeleteAccountDialog = false },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = null,
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.delete_account),
                        style = normal16.copy(color = OnPrimaryContainerLight)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.are_you_sure_you_want_to_delete),
                        style = normal16.copy(color = OnSurfaceVariantLight)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            settingsViewModel.deleteAccount()
                            openDeleteAccountDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            "Delete",
                            style = medium14
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDeleteAccountDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = medium14
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            )
        }
    }

    Scaffold(
        Modifier.fillMaxWidth(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Settings",
                colors = TopAppBarDefaults.topAppBarColors(SurfaceVariantLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                text = stringResource(id = R.string.security),
                style = semiBold18.copy(color = OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.change_master_password),
                    onClick = {
                        navController.navigate(Screen.UpdateMasterKeyScreen.name)
                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = OnSurfaceVariantLight
                )

                UCSwitchCard(
                    itemName = stringResource(R.string.unlock_with_biometric),
                    isChecked = biometricAuthState,
                    onChecked = {
                        settingsViewModel.showBiometricPrompt(context as MainActivity)
                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = OnSurfaceVariantLight
                )

                UCSwitchCard(
                    itemName = stringResource(R.string.take_in_app_screenshots),
                    isChecked = isScreenshotEnabled,
                    onChecked = {
                        settingsViewModel.setScreenshotEnabled(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
//                text = "Language",
//                style = bold20.copy(color = OnPrimaryContainerLight)
//            )

//            Spacer(modifier = Modifier.height(14.dp))
//
//            SettingsItemGroup {
//                UCSettingsCard(
//                    itemName = "App language",
//                    onClick = {
//
//                    }
//                )
//            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                text = stringResource(R.string.danger_zone),
                style = semiBold18.copy(color = OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.log_out),
                    textColor = Color.Red,
                    onClick = {
                        openLogoutDialog = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.delete_account),
                    textColor = Color.Red,
                    onClick = {
                        openDeleteAccountDialog = true
                    }
                )
            }
        }
    }
}