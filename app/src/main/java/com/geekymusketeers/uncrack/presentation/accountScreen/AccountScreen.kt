package com.geekymusketeers.uncrack.presentation.accountScreen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.BuildConfig
import com.geekymusketeers.uncrack.components.AccountOption
import com.geekymusketeers.uncrack.components.ProfileContainer
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.medium32
import com.geekymusketeers.uncrack.ui.theme.normal14

@Composable
fun AccountScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
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
                    navController.navigate("profile_screen")
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "John Doe",
                    style = medium32.copy(color = OnPrimaryContainerLight)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, bottom = 18.dp),
                    text = "Security",
                    style = medium14.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(0,3)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.BIOMETRIC -> {}
                        AccountItems.MASTER_KEY -> {}
                        AccountItems.BLOCK_SS -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, bottom = 18.dp),
                    text = "Backup",
                    style = medium14.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(3, AccountItems.entries.size)) {
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
                        .padding(start = 16.dp, end = 18.dp, bottom = 18.dp),
                    text = "General",
                    style = medium14.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(3, AccountItems.entries.size)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.THEME -> {}
                        AccountItems.PASSWORD_GENERATOR -> {}
                        AccountItems.INVITE_FRIENDS -> {}
                        AccountItems.RATE_UNCRACK -> {}
                        AccountItems.SEND_FEEDBACK -> {}
                        AccountItems.PRIVACY_POLICY -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 18.dp, bottom = 18.dp),
                    text = "Danger Zone",
                    style = medium14.copy(color = OnPrimaryContainerLight)
                )
            }

            items(AccountItems.entries.subList(3, AccountItems.entries.size)) {
                AccountOption(it) { onClick ->
                    when(onClick) {
                        AccountItems.LOG_OUT -> {}
                        AccountItems.DELETE_ACCOUNT -> {}
                        else -> {}
                    }
                }
            }

            item {
                Text(
                    text = "App Version: ${BuildConfig.VERSION_NAME}",
                    style = normal14.copy(color = SurfaceTintLight)
                )
            }
        }
    }
}