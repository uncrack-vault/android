package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.medium18
import com.geekymusketeers.uncrack.ui.theme.medium26
import com.geekymusketeers.uncrack.ui.theme.normal14
import com.geekymusketeers.uncrack.ui.theme.strongPassword

@Composable
fun ShieldCard(
    count: Int,
    modifier: Modifier = Modifier,
    textColor: Color = strongPassword,
    text: String = "",
    subText: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
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
                text = text,
                color = textColor,
                style = medium18
            )

            Text(
                text = subText,
                style = normal14.copy(SurfaceTintLight)
            )

            Text(
                text = count.toString(),
                style = medium26
            )
        }
    }
}