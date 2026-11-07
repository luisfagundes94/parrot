package com.luisfagundes.feature.search.impl.data.autocomplete

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SortedWordIndexTest {

    private val index = SortedWordIndex(
        sequenceOf("Parrot", "park", "part", "party", "apple", "app", "app", "zebra", "  ", "o'clock"),
    )

    @Test
    fun `returns words with the prefix in alphabetical order`() {
        assertEquals(listOf("park", "parrot", "part", "party"), index.suggest("par", limit = 10))
    }

    @Test
    fun `is case and whitespace insensitive`() {
        assertEquals(listOf("zebra"), index.suggest("  ZEB ", limit = 10))
    }

    @Test
    fun `respects the limit`() {
        assertEquals(listOf("park", "parrot"), index.suggest("par", limit = 2))
    }

    @Test
    fun `de-duplicates words`() {
        assertEquals(listOf("app", "apple"), index.suggest("app", limit = 10))
    }

    @Test
    fun `matches punctuation inside words`() {
        assertEquals(listOf("o'clock"), index.suggest("o'", limit = 10))
    }

    @Test
    fun `returns empty for blank, unknown prefix or non positive limit`() {
        assertTrue(index.suggest("", limit = 10).isEmpty())
        assertTrue(index.suggest("xyz", limit = 10).isEmpty())
        assertTrue(index.suggest("par", limit = 0).isEmpty())
    }
}
