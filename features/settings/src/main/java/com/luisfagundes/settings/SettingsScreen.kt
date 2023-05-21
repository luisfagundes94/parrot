package com.luisfagundes.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.luisfagundes.framework.extension.appVersion
import com.luisfagundes.settings.components.SettingsRow
import com.luisfagundes.settings.components.SwitchRow
import com.luisfagundes.theme.spacing

const val PARROT_GITHUB_URL = "https://github.com/luisfagundes94/parrot"
private const val INTENT_TYPE = "text/plain"

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigateEvent: (SettingsNavigationEvent) -> Unit,
) {
    val isNightModeEnabled = remember { mutableStateOf(uiState.isNightMode) }

    SettingsContent(
        modifier = Modifier,
        isNightModeEnabled = isNightModeEnabled.value,
        onNightModeChanged = { isChecked ->
            isNightModeEnabled.value = isChecked
            onEvent(SettingsEvent.OnSaveTheme(isChecked))
        },
        onNavigateToLanguages = {
            onNavigateEvent(SettingsNavigationEvent.NavigateToLanguages)
        },
        onNavigateToAbout = {
            onNavigateEvent(SettingsNavigationEvent.NavigateToAbout)
        },
    )
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    isNightModeEnabled: Boolean,
    onNightModeChanged: (Boolean) -> Unit,
    onNavigateToLanguages: () -> Unit,
    onNavigateToAbout: () -> Unit,
) {
    val context = LocalContext.current

    SettingsContainer(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            SwitchRow(
                modifier = modifier,
                checked = isNightModeEnabled,
                onCheckedChange = onNightModeChanged,
            )
            Divider(modifier.padding(horizontal = MaterialTheme.spacing.small))
            SettingsRow(
                modifier = modifier,
                title = stringResource(R.string.languages),
                onClick = onNavigateToLanguages,
            )
            Divider(modifier.padding(horizontal = MaterialTheme.spacing.small))
            SettingsRow(
                modifier = modifier,
                title = stringResource(R.string.share_app),
                onClick = { shareAppLink(context) },
            )
            Divider(modifier.padding(horizontal = MaterialTheme.spacing.small))
            SettingsRow(
                modifier = modifier,
                title = stringResource(R.string.about),
                onClick = onNavigateToAbout,
            )
            Divider(modifier.padding(horizontal = MaterialTheme.spacing.small))
            SettingsRow(
                modifier = modifier,
                title = stringResource(R.string.app_version),
                text = LocalContext.current.appVersion(),
                onClick = {},
            )
        }
    }
}

@Composable
private fun SettingsContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.padding(MaterialTheme.spacing.default),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        content.invoke()
    }
}

private fun shareAppLink(context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, PARROT_GITHUB_URL)
        type = INTENT_TYPE
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
