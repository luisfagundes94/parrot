package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextArea(
    onValueChange: (String) -> Unit = {},
    placeholder: String
) {
    var inputText by remember { mutableStateOf("") }
    onValueChange(inputText)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            placeholder = {
                Text(
                    text = placeholder,
                    style = LocalTextStyle.current.copy(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                )
            },
            value = inputText,
            onValueChange = { newText ->
                inputText = newText
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}