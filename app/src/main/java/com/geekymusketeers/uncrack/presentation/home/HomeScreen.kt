package com.geekymusketeers.uncrack.presentation.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.CategoryCard
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.medium24
import com.geekymusketeers.uncrack.ui.theme.normal22

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxWidth()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(BackgroundLight)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.no_user_profile_picture),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .clickable { navController.navigate("profile_screen") }
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Hello, Aritra",
                        style = medium24.copy(Color.Black)
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.manager_passwords),
                    style = normal22.copy(Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                CategoryCard(
                    icon = R.drawable.category_social,
                    text = stringResource(id = R.string.social),
                    onClick = {

                    }
                )

                CategoryCard(
                    icon = R.drawable.category_brower,
                    text = stringResource(R.string.browser),
                    onClick = {

                    }
                )

                CategoryCard(
                    icon = R.drawable.category_card,
                    text = stringResource(R.string.card),
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.recently_used),
                style = normal22.copy(Color.Black)
            )

            // TODO: FAV list
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreen(
        navController = rememberNavController(),
        modifier = Modifier
    )
}