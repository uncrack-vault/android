package com.geekymusketeers.uncrack.presentation.tools

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.presentation.tools.viewModel.ShieldViewModel
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.medium18
import com.geekymusketeers.uncrack.ui.theme.medium28
import com.geekymusketeers.uncrack.ui.theme.normal14

@Composable
fun ToolsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = Modifier.fillMaxWidth()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp),
        ) {

            Text(
                text = stringResource(R.string.tools),
                style = medium28.copy(OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.PasswordGeneratorScreen.name)
                    }
                    .background(Color.White)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x0D666666),
                        ambientColor = Color(0x0D666666)
                    )
                    .padding(horizontal = 16.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.generate_password_new),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(id = R.string.password_generator),
                        color = Color.Black,
                        style = medium18
                    )

                    Text(
                        text = stringResource(R.string.quickly_generate_your_new_secure_passwords),
                        color = Color.Gray,
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
                    .background(Color.White)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x0D666666),
                        ambientColor = Color(0x0D666666)
                    )
                    .padding(horizontal = 16.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(R.string.password_health),
                        color = Color.Black,
                        style = medium18
                    )

                    Text(
                        text = stringResource(R.string.identify_passwords_health),
                        color = Color.Gray,
                        style = normal14

                    )
                }
            }
        }
    }
}
