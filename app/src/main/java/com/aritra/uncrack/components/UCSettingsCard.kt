package com.aritra.uncrack.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritra.uncrack.R
import com.aritra.uncrack.ui.theme.OnSurfaceLight
import com.aritra.uncrack.ui.theme.medium18

@Composable
fun UCSettingsCard(
    itemName: String,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    textColor: Color = OnSurfaceLight,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconId != null) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(iconId),
                contentDescription = stringResource(R.string.icon),
            )
        }
        Text(
            text = itemName,
            style = medium18.copy(textColor)
        )
    }
}
