package com.aritra.uncrack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aritra.uncrack.R
import com.aritra.uncrack.ui.theme.bold30
import com.aritra.uncrack.ui.theme.normal20

@Composable
fun NoInternetScreen(modifier: Modifier = Modifier) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.no_internet
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier.then(modifier)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Opps Network Error!",
            style = bold30
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Please try again later",
            style = normal20
        )
    }
}