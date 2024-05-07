package com.geekymusketeers.uncrack.presentation.shield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.components.ShieldCard
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.bold36
import com.geekymusketeers.uncrack.ui.theme.normal14

@Composable
fun ShieldScreen(
    modifier: Modifier = Modifier
) {
    val progressValue by remember { mutableFloatStateOf(0f) }
    val progressMessage by rememberSaveable { mutableStateOf("0") }

    Scaffold(
        modifier = modifier.fillMaxWidth()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.padding(top = 30.dp),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    progress = { progressValue },
                    modifier = Modifier.size(180.dp),
                    color = Color.Green,
                    strokeWidth = 10.dp,
                    trackColor = SurfaceTintLight,
                    strokeCap = StrokeCap.Round,
                )

                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = progressMessage,
                    style = bold36,
                    color = Color.Black,
                )

                Text(
                    modifier = Modifier.padding(top = 90.dp),
                    text = "OUT OF 100",
                    style = normal14.copy(color = SurfaceTintLight)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                content = {
                    items(4) {
                        ShieldCard()
                    }
                }
            )
        }
    }
}