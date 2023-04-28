package com.luisfagundes.extensions

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}