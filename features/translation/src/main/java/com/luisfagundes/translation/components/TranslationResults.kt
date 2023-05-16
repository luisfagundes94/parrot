package com.luisfagundes.translation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.composeComponents.LoadingView
import com.luisfagundes.framework.composeComponents.WarningView
import com.luisfagundes.framework.extension.capitalize
import com.luisfagundes.framework.extension.showToast
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import com.luisfagundes.translation.presentation.TranslationUiState

@Composable
fun TranslationResults(
  uiState: TranslationUiState,
  onSaveWord: (ScheduleData, Word) -> Unit,
) {
  val modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)

  showToast(
    shouldShow = uiState.wordSavedWithSuccess,
    message = stringResource(R.string.word_saved),
  )

  when {
    uiState.hasError -> WarningView(
      modifier = modifier,
      title = stringResource(R.string.warning_error),
      animationId = com.luisfagundes.theme.R.raw.warning,
    )

    uiState.isEmpty -> WarningView(
      modifier = modifier,
      title = stringResource(R.string.warning_empty),
      animationId = com.luisfagundes.theme.R.raw.warning,
    )

    uiState.isLoading -> LoadingView(
      modifier = modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.default),
    )

    uiState.wordList.isNotEmpty() -> SuccessView(
      words = uiState.wordList,
      onSaveWord = { scheduleData, word ->
        onSaveWord(scheduleData, word)
      },
    )
  }
}

@Composable
private fun SuccessView(
  words: List<Word>,
  onSaveWord: (ScheduleData, Word) -> Unit,
) {
  val modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)

  Spacer(modifier = modifier)
  OtherTranslations(
    words = words,
    onSaveWord = onSaveWord,
  )
  Spacer(modifier = modifier)
  Examples(words = words)
}

@Composable
private fun ContainerBox(
  container: @Composable () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.small,
      )
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.small,
      )
      .padding(MaterialTheme.spacing.small),
  ) {
    container.invoke()
  }
}

@Composable
private fun OtherTranslations(
  words: List<Word>,
  onSaveWord: (ScheduleData, Word) -> Unit,
) {
  if (words.all { it.translations.isEmpty() }) return

  var openConfirmationBottomSheet by remember { mutableStateOf(false) }

  ConfirmationBottomSheet(
    shouldOpenBottomSheet = openConfirmationBottomSheet,
    onDismiss = { openConfirmationBottomSheet = false },
    onConfirmClick = { scheduleData ->
      onSaveWord(
        scheduleData,
        words.first(),
      )
      openConfirmationBottomSheet = false
    },
  )

  ContainerBox {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = words.first().translations.first().text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
      )
      Spacer(modifier = Modifier.weight(1f))
      IconButton(
        onClick = {
          openConfirmationBottomSheet = !openConfirmationBottomSheet
        },
      ) {
        Icon(
          modifier = Modifier.scale(1.1f),
          imageVector = Icons.Default.BookmarkAdd,
          contentDescription = stringResource(R.string.desc_bookmark_word),
        )
      }
    }
    Spacer(
      modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
    )
    Text(
      text = "Other translations",
      style = MaterialTheme.typography.titleMedium,
    )
    words.forEach { word ->
      word.translations.forEach { translation ->
        TranslationItem(
          translation = translation,
          isFeatured = true,
        )
      }
    }
  }
}

@Composable
private fun Examples(
  words: List<Word>,
) {
  if (
    words.all { word ->
      word.translations.all { translation ->
        translation.examples.isEmpty()
      }
    }
  ) {
    return
  }

  ContainerBox {
    Text(
      text = getExamplesOf(words.first()),
      style = MaterialTheme.typography.titleMedium,
    )
    words.forEach { word ->
      word.translations.forEach { translation ->
        TranslationItem(
          translation = translation,
          isFeatured = false,
        )
      }
    }
  }
}

@Composable
private fun getExamplesOf(word: Word): AnnotatedString {
  val annotatedString = buildAnnotatedString {
    append(stringResource(R.string.title_examples_of))
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
      append(word.text)
    }
  }
  return annotatedString
}

@Composable
private fun TranslationItem(
  translation: Translation,
  isFeatured: Boolean,
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    if (isFeatured.not() and translation.examples.isEmpty()) return@Column
    Spacer(
      modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
    )
    Text(
      text = translation.wordType.capitalize(),
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.primary,
      fontWeight = FontWeight.Bold,
    )
    if (isFeatured.not()) {
      Examples(translation.examples)
    } else {
      Text(translation.text)
    }
  }
}
