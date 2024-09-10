package com.geekymusketeers.uncrack.ui.theme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val navigationBottomBarHeight: Dp
    @Composable
    get() = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }

val navigationTopBarHeight: Dp = 50.dp

val screenSize: DpSize
    @Composable
    get() = with(LocalConfiguration.current) {
        DpSize(
            width = screenWidthDp.dp,
            height = screenHeightDp.dp
        )
    }