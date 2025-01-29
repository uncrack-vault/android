package com.aritradas.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.ui.theme.BackgroundLight

@Composable
fun SettingsItemGroup(
    modifier: Modifier = Modifier,
    columnScope: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .widthIn(max = 500.dp)
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BackgroundLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        columnScope()
    }
}