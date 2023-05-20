package com.luisfagundes.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.settings.components.SettingsRow
import com.luisfagundes.settings.components.SwitchRow
import com.luisfagundes.theme.spacing

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigateToLanguages: () -> Unit,
) {
    val isNightModeEnabled = remember { mutableStateOf(uiState.isNightMode) }

    SettingsContent(
        modifier = Modifier,
        isNightModeEnabled = isNightModeEnabled.value,
        onNightModeChanged = { isChecked ->
            isNightModeEnabled.value = isChecked
            onEvent(SettingsEvent.OnSaveTheme(isChecked))
        },
        onNavigateToLanguages = onNavigateToLanguages,
    )
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    isNightModeEnabled: Boolean,
    onNightModeChanged: (Boolean) -> Unit,
    onNavigateToLanguages: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.general),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(bottom = MaterialTheme.spacing.small),
        )
        SwitchRow(
            modifier = modifier,
            checked = isNightModeEnabled,
            onCheckedChange = onNightModeChanged,
        )
        Divider(modifier.padding(top = MaterialTheme.spacing.verySmall))
        SettingsRow(
            modifier = modifier.padding(vertical = MaterialTheme.spacing.default),
            title = stringResource(R.string.languages),
            onClick = onNavigateToLanguages,
        )
    }
}
