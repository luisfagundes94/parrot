package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import com.luisfagundes.domain.models.Example
import com.luisfagundes.theme.spacing

@Composable
fun Examples(examples: List<Example>) {
  Spacer(
    modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
  )
  examples.forEach { example ->
    Text(text = example.destinationLanguage)
    Spacer(
      modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall),
    )
    Text(
      text = "\"${example.sourceLanguage}\"",
      fontStyle = FontStyle.Italic,
    )
  }
}
