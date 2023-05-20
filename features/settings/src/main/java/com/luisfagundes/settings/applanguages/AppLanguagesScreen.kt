package com.luisfagundes.settings.applanguages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luisfagundes.domain.models.AppLanguage
import com.luisfagundes.framework.extension.showToast
import com.luisfagundes.theme.spacing

@Composable
fun AppLanguagesScreen(
    uiState: AppLanguageUiState,
    onEvent: (AppLanguageEvent) -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
) {
    val shouldShowToast = remember { mutableStateOf(false)}

    showToast(
        shouldShow = shouldShowToast.value,
        message = "Language will be changed on next app start"
    )

    LazyColumn(
        modifier = Modifier
            .padding(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        content = {
            items(uiState.languages) {
                LanguageRow(
                    title = it.name,
                    isSelected = it.isSelected,
                    onClick = {
                        onEvent(AppLanguageEvent.OnSaveAppLanguage(it))
                        onLanguageClick(it)
                        shouldShowToast.value = true
                    },
                )
            }
        },
    )
}

@Composable
fun LanguageRow(
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = MaterialTheme.spacing.verySmall)
            .clickable { onClick() },
    ) {
        Text(
            text = title,
        )
        Spacer(Modifier.weight(1f))
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
            )
        }
    }
}
