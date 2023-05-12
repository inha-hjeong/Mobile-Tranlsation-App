package com.translator.translator_project

// Define a data class called MainScreenState
data class MainScreenState(
    // Declare a Boolean property called 'isButtonEnabled' with a default value of 'true'
    val isButtonEnabled: Boolean = true,
    // Declare a String property called 'toTranslate' with a default value of an empty string
    val toTranslate: String = "",
    // Declare a String property called 'translated' with a default value of an empty string
    val translated: String = ""
)
