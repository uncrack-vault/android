package com.aritradas.uncrack.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aritradas.uncrack.ui.theme.DMSansFontFamily

@Composable
fun UCButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIcon: Painter? = null,
    leadingIcon: Painter? = null,
    isLoading: Boolean = false,
    loadingText: String? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = 50.dp
            )
            .clip(RoundedCornerShape(100.dp))
            .then(modifier),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onSurface,
                strokeWidth = 2.dp
            )
            loadingText?.let {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = it,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = DMSansFontFamily,
                    fontSize = 14.sp
                )
            }
        } else {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.padding(end = 4.dp),
                    painter = it,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = DMSansFontFamily,
                fontSize = 14.sp
            )
            trailingIcon?.let {
                Icon(
                    modifier = Modifier.padding(start = 4.dp),
                    painter = it,
                    contentDescription = null
                )
            }
        }
    }
}