package com.aritradas.uncrack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.aritradas.uncrack.ui.theme.OnSurfaceLight
import com.aritradas.uncrack.ui.theme.SurfaceTintLight
import com.aritradas.uncrack.ui.theme.medium18
import com.aritradas.uncrack.ui.theme.normal14

@Composable
fun UCSwitchCard(
    itemName: String,
    itemSubText: String? = null,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    textColor: Color = OnSurfaceLight,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = itemName,
                style = medium18.copy(textColor)
            )

            if (!itemSubText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = itemSubText,
                    style = normal14.copy(SurfaceTintLight)
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
