package com.luisfagundes.framework.extension

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}