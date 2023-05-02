package com.luisfagundes.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.luisfagundes.domain.models.Language
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.components.InputTextArea
import com.luisfagundes.translation.components.SourceAndTargetLanguage
import com.luisfagundes.translation.components.TranslationResults
import com.luisfagundes.translation.presentation.TranslationEvent
import com.luisfagundes.translation.presentation.TranslationUiState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslationScreen(
    uiState: TranslationUiState,
    onEvent: (TranslationEvent) -> Unit = {}
) {
    var sourceLanguage by remember { mutableStateOf(uiState.sourceLang) }
    var targetLanguage by remember { mutableStateOf(uiState.targetLang) }
    var inputText by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.default)
            .padding(top = MaterialTheme.spacing.default)
            .verticalScroll(rememberScrollState())
            .clipToBounds()
    ) {
        SourceAndTargetLanguage(
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            onInvertLanguage = {
                invertLanguages(
                    sourceLanguage,
                    targetLanguage
                )
            }
        )
        Spacer(
            modifier = Modifier.padding(MaterialTheme.spacing.verySmall)
        )
        InputTextArea(
            onValueChange = { newText ->
                inputText = newText
            },
            placeholder = stringResource(R.string.enter_text_to_translate)
        )
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
        )
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(TranslationEvent.Translate(inputText))
            },
            modifier = Modifier.height(MaterialTheme.spacing.buttonSize)
        ) {
            Text(
                text = stringResource(R.string.translate),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
        )
        TranslationResults(
            uiState = uiState
        )
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
        )
    }
}

private fun invertLanguages(
    sourceLanguage: Language,
    targetLanguage: Language
) {
    var sourceLanguageTemp = sourceLanguage
    var targetLanguageTemp = targetLanguage
    sourceLanguageTemp = targetLanguageTemp.also {
        targetLanguageTemp = sourceLanguageTemp
    }
}


