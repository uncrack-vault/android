package com.geekymusketeers.uncrack.presentation.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geekymusketeers.uncrack.components.ShieldCard
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.presentation.tools.viewModel.ShieldViewModel
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordHealthScreen(
    navController: NavController,
    shieldViewModel: ShieldViewModel,
    modifier: Modifier = Modifier
) {

    val strongPassword by shieldViewModel.strongPasswordCount.observeAsState(0)
    val weakPassword by shieldViewModel.weakPasswordCount.observeAsState(0)

    LaunchedEffect(Unit) {
        shieldViewModel.getPasswords()
    }

    Scaffold(
        modifier.fillMaxWidth(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Password Health",
                colors = TopAppBarDefaults.topAppBarColors(SurfaceVariantLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp)
        ) {
            ShieldCard(
                text = "Strong Password",
                count = strongPassword
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShieldCard(
                text = "Weak Password",
                count = weakPassword
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShieldCard(
                text = "Reused Password",
                count = 12
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShieldCard(
                text = "Old Password",
                count = 12
            )

        }
    }
}