package com.luisfagundes.framework.extension

private const val EMPTY_STRING = ""

fun List<String>.convertListToString() = joinToString(", ")

fun String.Companion.empty() = EMPTY_STRING

fun String.formatDate() = split("-").reversed().joinToString("/")