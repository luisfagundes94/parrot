package com.luisfagundes.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luisfagundes.theme.spacing

@Composable
fun SettingsRow(
    modifier: Modifier,
    title: String,
    text: String = "",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Row(
            modifier = modifier.padding(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title)
            Spacer(modifier.weight(1f))
            if (text.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                )
            } else {
                Text(text = text)
            }
        }
    }
}
