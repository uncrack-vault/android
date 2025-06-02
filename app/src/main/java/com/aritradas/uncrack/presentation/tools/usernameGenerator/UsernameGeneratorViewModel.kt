package com.aritradas.uncrack.presentation.tools.usernameGenerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UsernameGeneratorViewModel @Inject constructor() : ViewModel() {

    private val adjectives = listOf(
        "Happy",
        "Sad",
        "Brave",
        "Clever",
        "Swift",
        "Calm",
        "Lively",
        "Shy",
        "Witty",
        "Bold"
    )
    private val nouns = listOf(
        "Ghost",
        "Trumpet",
        "Tiger",
        "Falcon",
        "Wizard",
        "Panda",
        "Rocket",
        "Shadow",
        "Lion",
        "Otter"
    )

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun generateUsername() {
        val adjective = adjectives.random()
        val noun = nouns.random()
        val number = Random.nextInt(100, 1000) // 3-digit number
        _username.value = "$adjective$noun$number"
    }
}