package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luisfagundes.translation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EveryXHoursChips(
    onSelectedOption: (String) -> Unit,
) {
    val options = listOf(2, 3, 4, 8, 12, 24).map { "Every $it hours" }

    var selectedOption by rememberSaveable { mutableStateOf(options[0]) }

    fun onChipClick(option: String) {
        selectedOption = option
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            item {
                FilterChip(
                    onClick = { onChipClick(option) },
                    label = { Text(option) },
                    selected = option == selectedOption,
                    shape = RoundedCornerShape(16.dp),
                )
            }
        }
        item {
            Button(
                onClick = { onSelectedOption(selectedOption) }
            ) {
                Text(
                    text = stringResource(id = R.string.btn_confirm)
                )
            }
        }
    }
}
