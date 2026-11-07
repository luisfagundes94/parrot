package com.luisfagundes.feature.search.impl.data.autocomplete

/**
 * Immutable, prefix-searchable index of words. Words are normalized (trimmed, lowercased,
 * de-duplicated) and sorted once, so a prefix lookup is a binary search plus a short scan.
 */
internal class SortedWordIndex(words: Sequence<String>) {

    private val sorted: List<String> = words
        .map { it.trim().lowercase() }
        .filter { it.isNotEmpty() }
        .distinct()
        .sorted()
        .toList()

    fun suggest(prefix: String, limit: Int): List<String> {
        val normalized = prefix.trim().lowercase()
        if (normalized.isEmpty() || limit <= 0) return emptyList()

        val result = ArrayList<String>(limit)
        var index = firstIndexNotBefore(normalized)
        while (index < sorted.size && result.size < limit && sorted[index].startsWith(normalized)) {
            result += sorted[index]
            index++
        }
        return result
    }

    /** Index of the first word that is >= [prefix]. */
    private fun firstIndexNotBefore(prefix: String): Int {
        var low = 0
        var high = sorted.size
        while (low < high) {
            val mid = (low + high) ushr 1
            if (sorted[mid] < prefix) low = mid + 1 else high = mid
        }
        return low
    }
}
