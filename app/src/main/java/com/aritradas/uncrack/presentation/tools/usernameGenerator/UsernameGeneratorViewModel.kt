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
        "Happy", "Sad", "Brave", "Clever", "Swift", "Calm", "Lively", "Shy", "Witty", "Bold",
        "Cheerful", "Gentle", "Fierce", "Nimble", "Quiet", "Energetic", "Mysterious", "Playful",
        "Serene", "Vibrant", "Graceful", "Mighty", "Sneaky", "Brilliant", "Curious", "Daring",
        "Elegant", "Faithful", "Jovial", "Keen", "Loyal", "Noble", "Peaceful", "Quick",
        "Radiant", "Steady", "Trusty", "Unique", "Valiant", "Wise", "Zealous"
    )
    private val nouns = listOf(
        "Ghost", "Trumpet", "Tiger", "Falcon", "Wizard", "Panda", "Rocket", "Shadow", "Lion", "Otter",
        "Eagle", "Phoenix", "Dragon", "Wolf", "Bear", "Fox", "Hawk", "Jaguar", "Leopard", "Lynx",
        "Shark", "Whale", "Dolphin", "Penguin", "Sparrow", "Raven", "Owl", "Swan", "Deer", "Horse",
        "Elephant", "Rhino", "Hippo", "Giraffe", "Zebra", "Kangaroo", "Koala", "Sloth", "Turtle", "Frog"
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