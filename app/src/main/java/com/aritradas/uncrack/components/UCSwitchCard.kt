package com.aritradas.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.ui.theme.medium18
import com.aritradas.uncrack.ui.theme.normal14

@Composable
fun UCSwitchCard(
    itemName: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    itemSubText: String? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = itemName,
                style = medium18.copy(color = textColor)
            )

            if (!itemSubText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = itemSubText,
                    style = normal14.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }

        Switch(
            modifier = Modifier.semantics {
                contentDescription = "Theme switcher"
            },
            checked = isChecked,
            onCheckedChange = onChecked
        )
    }
}