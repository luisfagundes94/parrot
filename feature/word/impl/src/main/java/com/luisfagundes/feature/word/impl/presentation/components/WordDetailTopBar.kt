package com.luisfagundes.feature.word.impl.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.word.impl.R

/** [isBookmarked] is null while there is no word to bookmark (loading/error), which hides the button. */
@Composable
internal fun WordDetailTopBar(
    isBookmarked: Boolean?,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundIconButton(
            onClick = onBackClick,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.go_back)
        )
        if (isBookmarked != null) {
            RoundIconButton(
                onClick = onBookmarkClick,
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(
                    if (isBookmarked) R.string.remove_bookmark else R.string.bookmark_word
                )
            )
        }
    }
}

@Composable
private fun RoundIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

@Composable
@PreviewLightDark
private fun WordDetailTopBarPreview() {
    ParrotTheme {
        Surface {
            WordDetailTopBar(isBookmarked = false, onBackClick = {}, onBookmarkClick = {})
        }
    }
}
