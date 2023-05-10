import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme

@Composable
fun getSurfaceContainerLowColor(colorScheme: ColorScheme): Color {
    return colorScheme.onSurface.copy(
        alpha = if (isSystemInDarkTheme()) 0.12f else 0.04f
    )
}

@Composable
fun getSurfaceHighEmphasis(colorScheme: ColorScheme): Color {
    return colorScheme.onSurface.copy(
        alpha = if (isSystemInDarkTheme()) 1f else 0.87f
    )
}