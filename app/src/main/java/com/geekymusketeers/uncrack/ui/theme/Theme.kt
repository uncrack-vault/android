package com.geekymusketeers.uncrack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel

private val DarkColorPalette = darkColorScheme()
private val LightColorPalette = lightColorScheme()

@Composable
fun UnCrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+,
//    themeViewModel: ThemeViewModel = hiltViewModel(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val themeState by themeViewModel.themeState.collectAsState()

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = LocalContext.current
//            if (themeState.isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)

        }

//        themeState.isDarkMode -> DarkColorPalette
        else -> LightColorPalette
    }
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor =  colorScheme.background.toArgb()
        }
    }

//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = typography,
//        shapes = shapes,
//        content = content
//    )
}