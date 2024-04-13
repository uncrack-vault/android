package com.geekymusketeers.uncrack.presentation.account

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.BuildConfig
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.AccountOption
import com.geekymusketeers.uncrack.components.ProfileContainer
import com.geekymusketeers.uncrack.components.ThemeDialog
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.sharedViewModel.ThemeViewModel
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.bold18
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.medium32
import com.geekymusketeers.uncrack.ui.theme.normal14
import com.geekymusketeers.uncrack.ui.theme.normal16
import com.geekymusketeers.uncrack.util.Constants.INVITE
import com.geekymusketeers.uncrack.util.Util

@Composable
fun AccountScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var openThemeDialog by remember { mutableStateOf(false) }
    var openLogoutDialog by remember { mutableStateOf(false) }

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

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileContainer {
                    navController.navigate(Screen.ProfileScreen.name)
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "John Doe",
                    style = medium32.copy(color = OnPrimaryContainerLight)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                    text = stringResource(id = R.string.security),
                    style = bold18.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(0,3)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.BIOMETRIC -> {}
                        AccountItems.CHANGE_MASTER_KEY -> {
                            navController.navigate(Screen.UpdateMasterKeyScreen.name)
                        }
                        AccountItems.BLOCK_SS -> {
                            themeViewModel.blockScreenShort()
                        }
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                    text = stringResource(R.string.backup),
                    style = bold18.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(3,5)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.EXPORT_IMPORT -> {}
                        AccountItems.BACKUP_RESTORE -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                    text = stringResource(R.string.general),
                    style = bold18.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(5,11)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.THEME -> {
                            openThemeDialog = true
                        }

                        AccountItems.PASSWORD_GENERATOR -> {
                            navController.navigate(Screen.PasswordGeneratorScreen.name)
                        }

                        AccountItems.INVITE_FRIENDS -> {
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_TEXT, INVITE)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }

                        AccountItems.RATE_UNCRACK -> {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(Util.PLAYSTORE_URL)
                            context.startActivity(intent)
                        }

                        AccountItems.SEND_FEEDBACK -> {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data = Uri.parse(context.resources.getString(R.string.mailTo))
                            context.startActivity(openURL)
                        }

                        AccountItems.PRIVACY_POLICY -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, top = 18.dp),
                    text = stringResource(R.string.danger_zone),
                    style = bold18.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(11, AccountItems.entries.size)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.LOG_OUT -> {
                            openLogoutDialog = true
                        }
                        AccountItems.DELETE_ACCOUNT -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = "Version: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    style = normal14.copy(color = SurfaceTintLight)
                )
                Text(
                    modifier = Modifier.padding(bottom = 150.dp),
                    text = stringResource(R.string.by_aritra_das),
                    style = normal14.copy(color = SurfaceTintLight)
                )
            }
        }
    }
}