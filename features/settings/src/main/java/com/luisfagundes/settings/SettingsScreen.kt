package com.luisfagundes.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.luisfagundes.settings.components.SwitchRow
import com.luisfagundes.theme.spacing

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit,
) {
    val isNightModeEnabled = remember { mutableStateOf(uiState.isNightMode) }

    SettingsContent(
        modifier = Modifier,
        isNightModeEnabled = isNightModeEnabled.value,
        onNightModeChanged = { isChecked ->
            isNightModeEnabled.value = isChecked
            onEvent(SettingsEvent.OnSaveTheme(isChecked))
        },
    )
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    isNightModeEnabled: Boolean,
    onNightModeChanged: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "General",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(bottom = MaterialTheme.spacing.small),
        )
        SwitchRow(
            modifier = Modifier,
            checked = isNightModeEnabled,
            onCheckedChange = onNightModeChanged,
        )
    }
}
