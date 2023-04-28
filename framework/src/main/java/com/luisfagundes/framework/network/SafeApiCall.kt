package com.luisfagundes.framework.network

suspend fun <T> safeApiCall(call: suspend () -> T): DataState<T> {
    val data = call()
    return try {
        if (data == null) DataState.Empty
        else DataState.Success(data)
    } catch (exception: Exception) {
        DataState.Error(exception)
    }
}