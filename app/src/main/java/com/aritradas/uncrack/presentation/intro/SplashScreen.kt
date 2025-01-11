package com.aritradas.uncrack.presentation.intro

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var animation by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (animation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = ""
    )
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        animation = true

        if (currentUser == null) {
            navController.navigate(Screen.OnboardingScreen.name)
        } else {
            navController.navigate(Screen.ConfirmMasterKeyScreen.name)
        }

    }

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundLight),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .alpha(alphaAnimation.value)
                    .size(120.dp),
                painter = painterResource(id = R.drawable.dark_uncrack_cutout),
                contentDescription = "uncrack_logo"
            )
        }
    }
}
