package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object UtilsKt {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}