package com.luisfagundes.translation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.domain.models.Language
import com.luisfagundes.theme.spacing

@Composable
fun LanguagePair(
  languagePair: Pair<Language, Language>?,
  onInvertLanguage: () -> Unit,
  onLanguageClicked: (isSource: Boolean) -> Unit = {},
) {
  if (languagePair == null) return

  val sourceLanguage = languagePair.first
  val targetLanguage = languagePair.second

  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = MaterialTheme.spacing.default)
      .padding(horizontal = MaterialTheme.spacing.small),

  ) {
    Text(
      text = sourceLanguage.name,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.clickable { onLanguageClicked(true) },
    )
    Icon(
      imageVector = Icons.Filled.ImportExport,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier
        .size(MaterialTheme.spacing.default)
        .rotate(90f)
        .weight(1f)
        .clickable { onInvertLanguage() },
    )
    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
    Text(
      text = targetLanguage.name,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.clickable { onLanguageClicked(false) },
    )
  }
}
