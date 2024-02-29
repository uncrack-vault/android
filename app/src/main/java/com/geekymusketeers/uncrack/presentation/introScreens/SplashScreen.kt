package com.geekymusketeers.uncrack.presentation.introScreens

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
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.ui.theme.Background
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
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

    LaunchedEffect(true) {
        animation = true
        delay(1000L)
        navController.popBackStack()
        navController.navigate(route = "onboarding_screen")
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .alpha(alphaAnimation.value)
                    .size(240.dp),
                painter = painterResource(id = R.drawable.dark_uncrack_cutout),
                contentDescription = "uncrack_logo"
            )
        }
    }
}
