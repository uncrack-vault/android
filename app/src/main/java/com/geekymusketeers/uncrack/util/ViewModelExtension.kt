package com.geekymusketeers.uncrack.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.runIO(function: suspend CoroutineScope.() -> Unit) =
    viewModelScope.launch(Dispatchers.IO) { function() }

fun ViewModel.runDefault(function: suspend CoroutineScope.() -> Unit) =
    viewModelScope.launch(Dispatchers.Default) { function() }