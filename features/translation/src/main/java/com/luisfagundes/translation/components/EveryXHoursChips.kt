package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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

private const val FIRST_OPTION = 0
private const val EVERY_DAY = 24
private const val EVERY_WEEK = 168
private const val EVERY_MONTH = 730
const val EVERY_2_HOURS = 2
private const val EVERY_4_HOURS = 4
private const val DONT_REPEAT = -1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EveryXHoursChips(
  onSelectedOption: (Int) -> Unit,
) {
  val options = listOf(EVERY_2_HOURS, EVERY_4_HOURS, EVERY_DAY, EVERY_WEEK, EVERY_MONTH)
  val textOptions = options.map { hour ->
    when (hour) {
      EVERY_DAY -> stringResource(R.string.every_day)
      EVERY_WEEK -> stringResource(R.string.every_week)
      EVERY_MONTH -> stringResource(R.string.every_month)
      DONT_REPEAT -> stringResource(R.string.dont_repeat)
      else -> stringResource(R.string.every_x_hours, hour)
    }
  }

  var selectedOption by rememberSaveable { mutableStateOf(options[FIRST_OPTION]) }

  fun onChipClick(option: Int) {
    selectedOption = option
  }

  LazyColumn(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
  ) {
    options.forEachIndexed { index, option ->
      item {
        FilterChip(
          onClick = { onChipClick(option) },
          label = { Text(textOptions[index]) },
          selected = option == selectedOption,
          shape = RoundedCornerShape(16.dp),
        )
      }
    }
    item {
      Button(
        onClick = {
          onSelectedOption(selectedOption)
        },
      ) {
        Text(
          text = stringResource(id = R.string.btn_confirm),
        )
      }
    }
  }
}
