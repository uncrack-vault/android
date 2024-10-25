package com.aritradas.uncrack.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.CategoryCard
import com.aritradas.uncrack.ui.theme.OnPrimaryContainerLight
import com.aritradas.uncrack.ui.theme.SurfaceVariantLight
import com.aritradas.uncrack.ui.theme.medium28
import com.aritradas.uncrack.ui.theme.normal22

@Composable
fun BrowseScreen(
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
                .background(SurfaceVariantLight)
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(R.string.browse),
                style = medium28.copy(OnPrimaryContainerLight)
            )

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
