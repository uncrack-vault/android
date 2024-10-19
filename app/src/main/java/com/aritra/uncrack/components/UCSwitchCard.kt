package com.aritra.uncrack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.aritra.uncrack.ui.theme.OnSurfaceLight
import com.aritra.uncrack.ui.theme.medium18

@Composable
fun UCSwitchCard(
    itemName: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    textColor: Color = OnSurfaceLight,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemName,
            style = medium18.copy(textColor)
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            modifier = Modifier.semantics {
                contentDescription = "Theme switcher"
            },
            checked = isChecked,
            onCheckedChange = onChecked
        )
    }
}