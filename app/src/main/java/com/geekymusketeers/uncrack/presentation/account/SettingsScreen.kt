package com.geekymusketeers.uncrack.presentation.account

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
import androidx.compose.runtime.getValue
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
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.SettingsItemGroup
import com.geekymusketeers.uncrack.components.ThemeDialog
import com.geekymusketeers.uncrack.components.UCSettingsCard
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.sharedViewModel.ThemeViewModel
import com.geekymusketeers.uncrack.sharedViewModel.UserViewModel
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.bold18
import com.geekymusketeers.uncrack.ui.theme.bold20
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.normal16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var openThemeDialog by remember { mutableStateOf(false) }
    var openLogoutDialog by remember { mutableStateOf(false) }
    val userData = userViewModel.state.value

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
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
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
                            // TODO: IMPL the logout logic
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
                .background(SurfaceVariantLight),
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                text = stringResource(id = R.string.security),
                style = bold20.copy(color = OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = "Change Master Password",
                    onClick = {

                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = SurfaceVariantLight
                )

                UCSettingsCard(
                    itemName = stringResource(R.string.unlock_with_biometric),
                    onClick = {

                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = SurfaceVariantLight
                )

                UCSettingsCard(
                    itemName = stringResource(R.string.take_in_app_screenshots),
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                text = "Language",
                style = bold20.copy(color = OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = "App language",
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                text = stringResource(R.string.appearance),
                style = bold20.copy(color = OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.theme),
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.log_out),
                    textColor = Color.Red,
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SettingsItemGroup {
                UCSettingsCard(
                    itemName = stringResource(R.string.delete_account),
                    textColor = Color.Red,
                    onClick = {

                    }
                )
            }
        }
    }
}