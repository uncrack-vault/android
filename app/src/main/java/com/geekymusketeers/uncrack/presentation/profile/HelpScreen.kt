package com.geekymusketeers.uncrack.presentation.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.SettingsItemGroup
import com.geekymusketeers.uncrack.components.UCSettingsCard
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
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
            SettingsItemGroup {
                UCSettingsCard(
                    itemName = "Send Feedback",
                    onClick = {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(context.resources.getString(R.string.mailTo))
                        context.startActivity(openURL)
                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = SurfaceVariantLight
                )

                UCSettingsCard(
                    itemName = "Terms of Services",
                    onClick = {

                    }
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = SurfaceVariantLight
                )

                UCSettingsCard(
                    itemName = "Privacy Policy",
                    onClick = {

                    }
                )
            }

        }

    }
}