package com.luisfagundes.feature.word.impl.presentation.utility

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/** Returns [text] with every case-insensitive occurrence of [word] styled with [style]. */
internal fun highlightWord(text: String, word: String, style: SpanStyle): AnnotatedString {
    if (word.isBlank()) return AnnotatedString(text)
    return buildAnnotatedString {
        var cursor = 0
        while (true) {
            val index = text.indexOf(word, startIndex = cursor, ignoreCase = true)
            if (index < 0) break
            append(text.substring(cursor, index))
            withStyle(style) { append(text.substring(index, index + word.length)) }
            cursor = index + word.length
        }
        append(text.substring(cursor))
    }
}
