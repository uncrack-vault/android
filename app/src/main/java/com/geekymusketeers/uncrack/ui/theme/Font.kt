package com.geekymusketeers.uncrack.ui.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.geekymusketeers.uncrack.R

val DMSansFontFamily = FontFamily(
    Font(R.font.dmsans_extralight, FontWeight.ExtraLight),
    Font(R.font.dmsans_thin, FontWeight.Thin),
    Font(R.font.dmsans_light, FontWeight.Light),
    Font(R.font.dmsans_regular, FontWeight.Normal),
    Font(R.font.dmsans_medium, FontWeight.Medium),
    Font(R.font.dmsans_semibold, FontWeight.SemiBold),
    Font(R.font.dmsans_bold, FontWeight.Bold),
    Font(R.font.dmsans_extrabold, FontWeight.ExtraBold),
    Font(R.font.dmsans_black, FontWeight.Black),
)

@Composable
fun Modifier.font16() =
    this.background(MaterialTheme.colorScheme.onPrimaryContainer)