package com.aritra.uncrack.presentation.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.uncrack.BuildConfig
import com.aritra.uncrack.R
import com.aritra.uncrack.components.ProfileContainer
import com.aritra.uncrack.components.SettingsItemGroup
import com.aritra.uncrack.components.UCSettingsCard
import com.aritra.uncrack.navigation.Screen
import com.aritra.uncrack.sharedViewModel.UserViewModel
import com.aritra.uncrack.ui.theme.BackgroundLight
import com.aritra.uncrack.ui.theme.OnSurfaceLight
import com.aritra.uncrack.ui.theme.SurfaceTintLight
import com.aritra.uncrack.ui.theme.SurfaceVariantLight
import com.aritra.uncrack.ui.theme.medium22
import com.aritra.uncrack.ui.theme.normal14
import com.aritra.uncrack.util.Constants

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val userData by userViewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceVariantLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundLight)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileContainer(
                    userViewModel = userViewModel,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = userData.name,
                    style = medium22.copy(color = OnSurfaceLight)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SettingsItemGroup {
            UCSettingsCard(
                itemName = stringResource(id = R.string.settings),
                iconId = R.drawable.settings_new,
                onClick = {
                    navHostController.navigate(Screen.SettingsScreen.name)
                }
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = SurfaceVariantLight
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = SurfaceVariantLight
            )

            UCSettingsCard(
                itemName = stringResource(R.string.help),
                iconId = R.drawable.help,
                onClick = {
                    navHostController.navigate(Screen.HelpScreen.name)
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        SettingsItemGroup {
            UCSettingsCard(
                itemName = stringResource(R.string.rate_uncrack),
                iconId = R.drawable.rating,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(Constants.PLAYSTORE_URL)
                    context.startActivity(intent)
                }
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = SurfaceVariantLight
            )

            UCSettingsCard(
                itemName = stringResource(R.string.invite_friends),
                iconId = R.drawable.share_app,
                onClick = {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, Constants.INVITE)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

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