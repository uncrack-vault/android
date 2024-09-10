package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.ui.theme.normal14

@Composable
fun PasswordStrengthIndicator(
    text: String,
    isMet: Boolean,
    modifier: Modifier = Modifier
) {

    val color = if (isMet) Color.Green else Color.Red
    val icon = if (isMet) Icons.Default.Done else Icons.Default.Clear
    val iconColor = if (isMet) Color.Green else Color.Red

    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(iconColor),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = text,
            color = color,
            style = normal14
        )
    }

}