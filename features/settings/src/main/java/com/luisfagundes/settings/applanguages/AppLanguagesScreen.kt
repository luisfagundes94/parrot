package com.luisfagundes.settings.applanguages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.commonsUi.ParrotTopBar
import com.luisfagundes.domain.models.AppLanguage
import com.luisfagundes.framework.extension.showToast
import com.luisfagundes.settings.R
import com.luisfagundes.theme.spacing

@Composable
fun AppLanguagesScreen(
    uiState: AppLanguageUiState,
    onEvent: (AppLanguageEvent) -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
    onBackPressed: () -> Unit,
) {
    val shouldShowToast = remember { mutableStateOf(false) }

    showToast(
        shouldShow = shouldShowToast.value,
        message = stringResource(R.string.language_change),
    )

    ParrotTopBar(
        name = stringResource(R.string.languages),
        onBackPressed = { onBackPressed() },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(MaterialTheme.spacing.default),
            content = {
                items(uiState.languages) {
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                onEvent(AppLanguageEvent.OnSaveAppLanguage(it))
                                onLanguageClick(it)
                                shouldShowToast.value = true
                            },
                        headlineContent = { Text(text = it.name) },
                        trailingContent = {
                            if (it.isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                )
                            }
                        },
                    )
                    Divider()
                }
            },
        )
    }
}