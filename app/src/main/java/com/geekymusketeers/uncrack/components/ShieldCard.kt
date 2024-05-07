package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.medium26

@Composable
fun ShieldCard(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .widthIn(min = 165.dp, 165.dp)
            .heightIn(min = 99.dp, 99.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .shadow(
                elevation = 5.dp,
                spotColor = Color(0x0D666666),
                ambientColor = Color(0x0D666666)
            )
            .padding(horizontal = 16.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "12",
                color = Color.Green,
                style = medium26

            )
            Text(
                text = "Safe Password",
                color = Color.Black,
                style = medium14
            )
        }
    }
}