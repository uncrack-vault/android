package com.aritradas.uncrack.presentation.tools.usernameGenerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UsernameGeneratorViewModel @Inject constructor() : ViewModel() {

    private val adjectives = arrayOf(
        "happy", "sad", "brave", "clever", "swift", "calm", "lively", "shy", "witty", "bold",
        "cheerful", "gentle", "fierce", "nimble", "quiet", "energetic", "mysterious", "playful",
        "serene", "vibrant", "graceful", "mighty", "sneaky", "brilliant", "curious", "daring",
        "elegant", "faithful", "jovial", "keen", "loyal", "noble", "peaceful", "quick",
        "radiant", "steady", "trusty", "unique", "valiant", "wise", "zealous"
    )
    private val nouns = arrayOf(
        "ghost", "trumpet", "tiger", "falcon", "wizard", "panda", "rocket", "shadow", "lion", "otter",
        "eagle", "phoenix", "dragon", "wolf", "bear", "fox", "hawk", "jaguar", "leopard", "lynx",
        "shark", "whale", "dolphin", "penguin", "sparrow", "raven", "owl", "swan", "deer", "horse",
        "elephant", "rhino", "hippo", "giraffe", "zebra", "kangaroo", "koala", "sloth", "turtle", "frog"
    )

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _includeNumbers = MutableLiveData(true)
    val includeNumbers: LiveData<Boolean> = _includeNumbers

    private val _useCapitalization = MutableLiveData(true)
    val useCapitalization: LiveData<Boolean> = _useCapitalization

    fun generateUsername() {
        _username.value = generateUsername(
            includeNumbers = _includeNumbers.value ?: true,
            useCapitalization = _useCapitalization.value ?: true,
        )
    }

    fun updateIncludeNumbers(include: Boolean) {
        _includeNumbers.value = include
    }

    fun updateUseCapitalization(use: Boolean) {
        _useCapitalization.value = use
    }

    private fun generateUsername(
        includeNumbers: Boolean = true,
        useCapitalization: Boolean = true
    ): String {
        var adjective = adjectives.random()
        var noun = nouns.random()
        val numberPart =
            if (includeNumbers)
                Random.nextInt(100, 1000).toString()
            else ""
        if (useCapitalization) {
            adjective = adjective.replaceFirstChar { it.uppercase() }
            noun = noun.replaceFirstChar { it.uppercase() }
        }
        val username = "$adjective$noun$numberPart"
        return username
    }
}