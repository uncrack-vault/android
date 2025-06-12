package com.aritradas.uncrack.presentation.tools

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.SurfaceVariantLight
import com.aritradas.uncrack.ui.theme.medium18
import com.aritradas.uncrack.ui.theme.normal14

@Composable
fun ToolsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    generatorIconId: Int = R.drawable.generate_password_new,
    shieldIconId: Int = R.drawable.shield
) {

    val context = LocalContext.current

    BackHandler(onBack = {
        (context as? android.app.Activity)?.finish()
    })

    Scaffold(
        modifier = Modifier.fillMaxWidth()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.GeneratorScreen.name)
                    }
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x0D666666),
                        ambientColor = Color(0x0D666666)
                    )
                    .padding(horizontal = 16.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = generatorIconId),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(id = R.string.generator),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = medium18
                    )

                    Text(
                        text = stringResource(R.string.quickly_generate_your_passwords_and_usernames),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = normal14
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.PasswordHealthScreen.name)
                    }
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x0D666666),
                        ambientColor = Color(0x0D666666)
                    )
                    .padding(horizontal = 16.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = shieldIconId),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(R.string.password_health),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = medium18
                    )

                    Text(
                        text = stringResource(R.string.identify_passwords_health),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = normal14

                    )
                }
            }
        }
    }
}