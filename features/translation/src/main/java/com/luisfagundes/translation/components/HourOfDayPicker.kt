package com.luisfagundes.translation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.framework.components.TimePickerDialog
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourOfDayPicker(
    onHourSelected: (Int) -> Unit,
) {
    var selectedHour by remember {
        mutableStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    }
    val state = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            modifier = Modifier
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                ),
            onClick = { showTimePicker = !showTimePicker },
        ) {
            Text(
                text = stringResource(R.string.select_time),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = selectedHour.toString() + "h",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            title = stringResource(R.string.select_time),
            onCancel = { showTimePicker = false },
            onConfirm = {
                selectedHour = state.hour
                showTimePicker = false
                onHourSelected(state.hour)
            },
        ) {
            TimeInput(
                state = state,
            )
        }
    }
    Spacer(Modifier.height(MaterialTheme.spacing.small))
}
