package com.luisfagundes.feature.word.impl.presentation.utility

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class HighlightedTextTest {

    private val style = SpanStyle(fontWeight = FontWeight.Bold)

    @Test
    fun `highlights a case-insensitive match keeping the original casing`() {
        // When
        val result = highlightWord("Pure Serendipity.", "serendipity", style)

        // Then
        assertEquals("Pure Serendipity.", result.text)
        assertEquals(listOf(AnnotatedString.Range(style, 5, 16)), result.spanStyles)
    }

    @Test
    fun `highlights every occurrence`() {
        // When
        val result = highlightWord("luck and luck", "luck", style)

        // Then
        assertEquals(listOf(0 to 4, 9 to 13), result.spanStyles.map { it.start to it.end })
    }

    @Test
    fun `returns plain text when the word is absent or blank`() {
        // When / Then
        assertTrue(highlightWord("Nothing here.", "luck", style).spanStyles.isEmpty())
        assertTrue(highlightWord("Nothing here.", " ", style).spanStyles.isEmpty())
        assertEquals("Nothing here.", highlightWord("Nothing here.", " ", style).text)
    }
}
