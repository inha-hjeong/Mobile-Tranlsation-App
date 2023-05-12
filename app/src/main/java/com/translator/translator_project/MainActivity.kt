package com.translator.translator_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.translator.translator_project.ui.theme.TranslatorTheme

class MainActivity : ComponentActivity() {

    // onCreate is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the parent class's onCreate method with the savedInstanceState
        super.onCreate(savedInstanceState)

        // Set the content of the activity using Jetpack Compose UI
        setContent {
            // Apply the TranslatorTheme, which defines the app's theme (colors, typography, etc.)
            TranslatorTheme {
                // Create a Surface, a Composable that provides background color and elevation
                Surface(
                    // Apply a Modifier to fill the available space with the Surface
                    modifier = Modifier.fillMaxSize(),
                    // Set the background color of the Surface using the current theme
                    color = MaterialTheme.colors.background
                ) {
                    // Call the MainScreen() Composable, which defines the UI of the main screen
                    MainScreen()
                }
            }
        }
    }
}



