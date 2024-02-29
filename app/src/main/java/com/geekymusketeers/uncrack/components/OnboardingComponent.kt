package com.geekymusketeers.uncrack.components

import androidx.compose.runtime.Composable
import com.geekymusketeers.uncrack.presentation.introScreens.model.OnBoardingItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.ui.theme.bold22
import com.geekymusketeers.uncrack.ui.theme.medium16

@Composable
fun OnboardingComponent(item: OnBoardingItem) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = "onboardimage",
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        )
        Spacer(modifier = Modifier.padding(50.dp))

        Text(
            text = item.title,
            style = bold22,
            color = Color.Black,
        )

        Text(
            text = item.text,
            textAlign = TextAlign.Center,
            style = medium16,
            color = Color.Black,
        )
    }
}