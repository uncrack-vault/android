package com.geekymusketeers.uncrack.presentation.shield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.normal12

@Composable
fun ShieldScreen(
    modifier: Modifier = Modifier
) {
    val progressValue by remember { mutableFloatStateOf(0f) }
    val progressMessage by rememberSaveable { mutableStateOf("") }

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

            Box(contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    progress = { progressValue },
                    modifier = Modifier.size(180.dp),
                    color = Color.Green,
                    strokeWidth = 10.dp,
                    trackColor = SurfaceTintLight,
                    strokeCap = StrokeCap.Round,
                )

                Text(
                    text = progressMessage,
                    style = normal12,
                    color = OnPrimaryContainerLight,
                )

            }

        }
    }
}