package com.luisfagundes.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraSmall: Dp = 4.dp,
    val verySmall: Dp = 8.dp,
    val small: Dp = 16.dp,
    val default: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 42.dp,
    val buttonSize: Dp = 50.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
