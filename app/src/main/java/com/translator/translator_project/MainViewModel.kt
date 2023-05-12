package com.translator.translator_project

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainViewModel : ViewModel() {

    // Declare a private mutable state of MainScreenState
    private val _state = mutableStateOf(
        MainScreenState()
    )

    // Declare a public read-only state of MainScreenState
    val state: State<MainScreenState> = _state

    // Define a function to handle the change in the text to be translated
    fun onTextToBeTranslatedChange(text: String) {
        // Update the toTranslate property in the state
        _state.value = state.value.copy(
            toTranslate = text
        )
    }

    // Define a function to handle the translation button click
    fun onTranslateButtonClick(
        text: String,
        sourceLanguageCode: String,
        targetLanguageCode: String,
        context: Context
    ) {
        // Create TranslatorOptions with source and target language codes
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()

        // Get a language translator instance with the specified options
        val languageTranslator = Translation
            .getClient(options)

        // Translate the given text using the language translator instance
        languageTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                // Update the translated property in the state with the translated text
                _state.value = state.value.copy(
                    translated = translatedText
                )
            }
            .addOnFailureListener {
                // Show a toast message indicating the download has started
                Toast.makeText(
                    context,
                    "Downloading started..",
                    Toast.LENGTH_SHORT
                ).show()
                // Call the downloadModelIfNotAvailable function to download the model if needed
                downloadModelIfNotAvailable(languageTranslator, context)
            }
    }

    // Define a private function to download the language model if not available
    private fun downloadModelIfNotAvailable(
        languageTranslator: Translator,
        context: Context
    ) {
        // Update the isButtonEnabled property in the state to false
        _state.value = state.value.copy(
            isButtonEnabled = false
        )

        // Create download conditions that require Wi-Fi connection
        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        // Download the language model if needed using the given conditions
        languageTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Show a toast message indicating the model has been downloaded successfully
                Toast.makeText(
                    context,
                    "Downloaded model successfully..",
                    Toast.LENGTH_SHORT
                ).show()

                // Update the isButtonEnabled property in the state to true
                _state.value = state.value.copy(
                    isButtonEnabled = true
                )
            }
            .addOnFailureListener {
                // Show a toast message indicating an error occurred during model download
                Toast.makeText(
                    context,
                    "Some error occurred couldn't download language model..",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}