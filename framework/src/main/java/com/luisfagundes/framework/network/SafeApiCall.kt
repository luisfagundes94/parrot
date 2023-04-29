package com.luisfagundes.framework.network

import android.util.Log

suspend fun <T> safeApiCall(call: suspend () -> T): DataState<T> {
    val data = call()
    return try {
        if (data == null) DataState.Empty
        else DataState.Success(data)
    } catch (exception: Exception) {
        Log.d("safeApiCall", exception.stackTraceToString())
        DataState.Error(exception)
    }
}