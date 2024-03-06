package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekymusketeers.uncrack.ui.theme.BackgroundDark
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily

@Composable
fun UCStrokeButton(
    text: String,
    modifier: Modifier = Modifier,
    strokeColor: Color = BackgroundDark,
    textColor: Color = BackgroundDark,
    leadingIcon: Painter? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = 50.dp
            )
            .clip(RoundedCornerShape(100.dp))
            .then(modifier),
        onClick = { onClick() },
        shape = CircleShape,
        enabled = enabled,
        border = BorderStroke(1.dp, strokeColor)
    ) {
        leadingIcon?.let {
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                painter = it,
                contentDescription = null,
                tint = strokeColor
            )
        }
        Text(
            text = text,
            color = textColor,
            fontFamily = DMSansFontFamily,
            fontSize = 14.sp
        )
    }
}