package com.luisfagundes.framework.composeComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.luisfagundes.Delayed

@Composable
fun LoadingView(modifier: Modifier = Modifier, delayMillis: Long = 100L) {
  Delayed(delayMillis = delayMillis) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = when (modifier == Modifier) {
        true -> Modifier.fillMaxSize()
        false -> modifier
      },
    ) {
      ProgressIndicator()
    }
  }
}

@Composable
fun ProgressIndicator(
  modifier: Modifier = Modifier,
  strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
  color: Color = MaterialTheme.colorScheme.secondary,
) {
  CircularProgressIndicator(modifier, color, strokeWidth)
}
