package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
  shouldOpenBottomSheet: Boolean,
  onDismiss: () -> Unit,
  onConfirmClick: (ScheduleData) -> Unit,
) {
  val scope = rememberCoroutineScope()
  val bottomSheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = false,
  )
  var scheduleData by remember { mutableStateOf(ScheduleData()) }

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
          text = stringResource(R.string.title_reminder),
          style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.small))
        EveryXHoursChips(
          onSelectedOption = { everyHour ->
            scope.launch {
              bottomSheetState.hide()
              onConfirmClick(
                scheduleData.copy(intervalHours = everyHour),
              )
            }
          },
        )
      }
    }
  }
}
