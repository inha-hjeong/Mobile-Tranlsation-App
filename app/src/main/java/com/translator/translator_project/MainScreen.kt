package com.translator.translator_project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import com.translator.translator_project.ui.theme.darkBackground


@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val languages = listOf(
        Language(TranslateLanguage.ENGLISH, "English"),
        Language(TranslateLanguage.RUSSIAN, "Russian"),
        Language(TranslateLanguage.SPANISH, "Spanish"),
        Language(TranslateLanguage.FRENCH, "French"),
        Language(TranslateLanguage.GERMAN, "German"),
        Language(TranslateLanguage.KOREAN, "Korean"),
    )

    // hold the currently selected source language
    val selectedSourceLanguage = remember { mutableStateOf(languages[0]) }
    val selectedTargetLanguage = remember { mutableStateOf(languages[1]) }
    // manage the expanded state of the source language dropdown list
    val sourceLanguageExpanded = remember { mutableStateOf(false) }
    val targetLanguageExpanded = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(darkBackground)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Top Title
        Text(
            text = "Translator",
            modifier = Modifier.padding(top = 30.dp),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        // Dropdown menu to select Source Language
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            TextButton(
                onClick = { sourceLanguageExpanded.value = !sourceLanguageExpanded.value },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Source: ${selectedSourceLanguage.value.displayName}")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Source Language Dropdown"
                )
            }
            DropdownMenu(
                expanded = sourceLanguageExpanded.value,
                onDismissRequest = { sourceLanguageExpanded.value = false }
            ) {
                languages.forEach { language ->
                    DropdownMenuItem(
                        onClick = {
                            sourceLanguageExpanded.value = false
                            selectedSourceLanguage.value = language
                        }
                    ) {
                        Text(text = language.displayName)
                    }
                }
            }
        }

        // Source Text to translate
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
                .border(0.5.dp, Color.White),
            value = state.toTranslate,
            onValueChange = {
                viewModel.onTextToBeTranslatedChange(it)
            },
            singleLine = false,
            label = { Text("Text to be translated") },
            colors = TextFieldDefaults.textFieldColors(
                Color.White,
                unfocusedLabelColor = Color.White
            )
        )

        // Dropdown menu to select Target Language
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ) {
            TextButton(
                onClick = { targetLanguageExpanded.value = !targetLanguageExpanded.value },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Target: ${selectedTargetLanguage.value.displayName}")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Target Language Dropdown"
                )
            }
            DropdownMenu(
                expanded = targetLanguageExpanded.value,
                onDismissRequest = { targetLanguageExpanded.value = false }
            ) {
                languages.forEach { language ->
                    DropdownMenuItem(
                        onClick = {
                            targetLanguageExpanded.value = false
                            selectedTargetLanguage.value = language
                        }
                    ) {
                        Text(text = language.displayName)
                    }
                }
            }
        }

        // Translated Text
        if (state.translated.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .border(BorderStroke(1.dp, color = Color.White))
            ) {
                Text(
                    text = state.translated,
                    modifier = Modifier
                        .padding(16.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Button to Translate
        Button(onClick = {
            viewModel.onTranslateButtonClick(
                text = state.toTranslate,
                sourceLanguageCode = selectedSourceLanguage.value.code,
                targetLanguageCode = selectedTargetLanguage.value.code,
                context = context
            )
        },
            enabled = state.isButtonEnabled,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .height(48.dp) // Set the button height
                .fillMaxWidth(0.8f), // Set the button width to 80% of the screen width
            shape = RoundedCornerShape(12.dp) // Add rounded corners
        ) {
            Text(
                text = "Translate",
                fontSize = 18.sp, // Increase font size
                fontWeight = FontWeight.Bold // Set font weight to bold
            )
        }
    }
}