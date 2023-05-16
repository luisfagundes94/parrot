package com.luisfagundes.framework.extension

import java.util.Locale

private const val EMPTY_STRING = ""

fun List<String>.convertListToString() = joinToString(", ")

fun String.Companion.empty() = EMPTY_STRING

fun String.formatDate() = split("-").reversed().joinToString("/")

fun String.capitalize() = replaceFirstChar {
  if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
