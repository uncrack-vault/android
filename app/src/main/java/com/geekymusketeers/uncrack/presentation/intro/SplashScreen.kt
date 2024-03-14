package com.geekymusketeers.uncrack.presentation.intro

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            )
        )
        super.onCreate(savedInstanceState)

        setContent {
            UnCrackTheme {
                SplashContent(this@SplashScreen)
            }
        }
    }
}
@Composable
fun SplashContent(activity: Activity, modifier: Modifier = Modifier) {

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

    LaunchedEffect(Unit) {
        animation = true

        // TODO: Need to change the logic for old and new user
        val intent = Intent(activity, OnboardingScreen::class.java)

        activity.run {
            delay(2000L)
            startActivity(intent)
            finish()
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
