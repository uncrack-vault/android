package com.geekymusketeers.uncrack.presentation.browse

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.CategoryCard
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.medium18
import com.geekymusketeers.uncrack.ui.theme.medium28
import com.geekymusketeers.uncrack.ui.theme.normal14
import com.geekymusketeers.uncrack.ui.theme.normal22

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 17.dp)
                    .clickable {
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.category_card), // Need to change the image
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Folder",
                        color = Color.Black,
                        style = medium18
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 17.dp)
                    .clickable {
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.favorite_border),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Favourite",
                        color = Color.Black,
                        style = medium18
                    )

                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 17.dp)
                    .clickable {
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Trash",
                        color = Color.Black,
                        style = medium18
                    )
                }
            }
        }
    }
}
