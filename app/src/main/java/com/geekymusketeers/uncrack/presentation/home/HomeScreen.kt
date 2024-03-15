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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.CategoryCard
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.bold20
import com.geekymusketeers.uncrack.ui.theme.normal14
import com.geekymusketeers.uncrack.ui.theme.normal16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    var searchQuery by rememberSaveable { mutableStateOf("") }

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

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Hello, Aritra",
                    style = bold20.copy(Color.Black)
                )

                Image(
                    painter = painterResource(id = R.drawable.no_user_profile_picture),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(
                        text = "Search here",
                        style = normal16
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            ) { }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Manager Passwords",
                    style = bold20.copy(Color.Black)
                )

                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable {
                            navController.navigate("category_screen")
                        },
                    text = "See all",
                    style = normal14.copy(Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                CategoryCard(
                    icon = R.drawable.category_social,
                    text = "Social",
                    onClick = {

                    }
                )

                CategoryCard(
                    icon = R.drawable.category_brower,
                    text = "Browser",
                    onClick = {

                    }
                )

                CategoryCard(
                    icon = R.drawable.category_card,
                    text = "Card",
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Favourites",
                style = bold20.copy(Color.Black)
            )


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