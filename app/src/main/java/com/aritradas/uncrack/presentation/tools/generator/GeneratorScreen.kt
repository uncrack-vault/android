package com.aritradas.uncrack.presentation.tools.generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritradas.uncrack.components.UCTopAppBar
import com.aritradas.uncrack.presentation.tools.passwordGenerator.PasswordGenerator
import com.aritradas.uncrack.presentation.tools.passwordGenerator.PasswordGeneratorViewModel
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.PrimaryLight
import com.aritradas.uncrack.ui.theme.SurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(
    navController: NavHostController,
    passwordGeneratorViewModel: PasswordGeneratorViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Password") }

    Scaffold(
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Generator",
                colors = TopAppBarDefaults.topAppBarColors(BackgroundLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { selectedTab = "Password" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "Password") PrimaryLight else SurfaceLight
                    )
                ) {
                    Text("Password")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { selectedTab = "Username" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "Username") PrimaryLight else SurfaceLight
                    )
                ) {
                    Text("Username")
                }
            }

            // Change the content based on the selected tab
            when (selectedTab) {
                "Password" -> PasswordGenerator(
                    navController = navController,
                    passwordGeneratorViewModel = passwordGeneratorViewModel,
                    modifier = Modifier.weight(1f)
                )

                "Username" -> UsernameGeneratorPlaceholder(modifier = Modifier.weight(1f))
            }
        }
    }
}

// Delete this placeholder when the UsernameGenerator is implemented
@Composable
fun UsernameGeneratorPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Username generator coming soon!")
    }
}