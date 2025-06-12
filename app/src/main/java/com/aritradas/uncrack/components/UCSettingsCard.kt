package com.aritradas.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.R
import com.aritradas.uncrack.ui.theme.medium18
import com.aritradas.uncrack.ui.theme.normal14

@Composable
fun UCSettingsCard(
    itemName: String,
    itemSubText: String? = null,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconId != null) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(iconId),
                contentDescription = stringResource(R.string.icon),
            )
            Spacer(modifier = Modifier.width(12.dp))

        }

        Column(
            modifier = Modifier.fillMaxWidth(),
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
                    style = normal14.copy(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

