package com.geekymusketeers.uncrack.presentation.tools.passwordHealth

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.ShieldCard
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.strongPassword
import com.geekymusketeers.uncrack.ui.theme.weakPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordHealthScreen(
    navController: NavController,
    shieldViewModel: PassHealthViewModel,
    modifier: Modifier = Modifier
) {

    val strongPasswordObserver by shieldViewModel.strongPasswordCount.observeAsState(0)
    val weakPasswordObserver by shieldViewModel.weakPasswordCount.observeAsState(0)

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
                text = stringResource(R.string.strong_passwords),
                subText = stringResource(R.string.boosts_security_with_robust_passwords_defending_against_hacks),
                count = strongPasswordObserver,
                textColor = strongPassword
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShieldCard(
                text = stringResource(R.string.weak_passwords),
                subText = stringResource(R.string.makes_your_accounts_easy_to_hack),
                count = weakPasswordObserver,
                textColor = weakPassword
            )
        }
    }
}