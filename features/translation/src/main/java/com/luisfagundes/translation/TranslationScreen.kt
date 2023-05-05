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
import com.luisfagundes.translation.components.InputTextArea
import com.luisfagundes.translation.components.LanguagePair
import com.luisfagundes.translation.components.TranslationResults
import com.luisfagundes.translation.presentation.TranslationEvent
import com.luisfagundes.translation.presentation.TranslationUiState
import com.luisfagundes.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslationScreen(
    uiState: TranslationUiState,
    onEvent: (TranslationEvent) -> Unit = {},
    onLanguageClicked: () -> Unit = {}
) {
    var countryPair by remember { mutableStateOf(uiState.languagePair) }
    var inputText by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val spacing = MaterialTheme.spacing

    Column(
        modifier = Modifier
            .padding(horizontal = spacing.default)
            .padding(top = spacing.default)
            .verticalScroll(rememberScrollState())
            .clipToBounds()
    ) {
        LanguagePair(
            languagePair = countryPair,
            onInvertLanguage = {
                onEvent(
                    TranslationEvent.InvertCountries(countryPair)
                )
            },
            onLanguageClicked = onLanguageClicked
        )
        Spacer(
            modifier = Modifier.padding(spacing.verySmall)
        )
        InputTextArea(
            onValueChange = { inputText = it },
            placeholder = stringResource(R.string.enter_text_to_translate)
        )
        Spacer(
            modifier = Modifier.padding(spacing.verySmall)
        )
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(TranslationEvent.Translate(inputText))
            },
            modifier = Modifier.height(spacing.buttonSize)
        ) {
            Text(
                text = stringResource(R.string.translate),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(
            modifier = Modifier.padding(vertical = spacing.small)
        )
        TranslationResults(
            uiState = uiState
        )
        Spacer(
            modifier = Modifier.padding(vertical = spacing.small)
        )
    }
}


