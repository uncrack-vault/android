package com.geekymusketeers.uncrack.util

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}

@Composable
fun BackPressHandler() {

    var showToast by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var backPressed by remember {
        mutableStateOf<BackPress>(BackPress.Idle)
    }

    if (showToast) {
        Toast.makeText(context, "Press again to exit", Toast.LENGTH_LONG).show()
        showToast = false
    }

    LaunchedEffect(key1 = backPressed) {
        if (backPressed == BackPress.InitialTouch) {
            delay(2000)
            backPressed = BackPress.Idle
        }
    }

    BackHandler(backPressed == BackPress.Idle) {
        backPressed = BackPress.InitialTouch
        showToast = true
    }
}