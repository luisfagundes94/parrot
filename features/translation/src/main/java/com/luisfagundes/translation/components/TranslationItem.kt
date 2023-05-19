package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.framework.extension.capitalize
import com.luisfagundes.theme.spacing

@Composable
fun TranslationItem(
    translation: Translation,
    isFeatured: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (isFeatured.not() and translation.examples.isEmpty()) return@Column
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
        )
        Text(
            text = translation.wordType.capitalize(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        if (isFeatured.not()) {
            ExampleList(translation.examples)
        } else {
            Text(translation.text)
        }
    }
}
