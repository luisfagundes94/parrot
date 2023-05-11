package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
    shouldOpenBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    if (shouldOpenBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                    onDismiss()
                }
            },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default),
            ) {
                Text(
                    text = "How often would you like to receive the word?",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                EveryXHoursChips(onSelectedOption = {
                    scope.launch {
                        bottomSheetState.hide()
                        onConfirmClick()
                    }
                })
            }
        }
    }
}
