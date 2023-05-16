package com.luisfagundes.framework.network

import timber.log.Timber

@JvmName("safeApiCall")
suspend fun <T> safeApiCall(call: suspend () -> T): DataState<T> {
  val data = call()
  return try {
    if (data == null) {
      DataState.Empty
    } else {
      DataState.Success(data)
    }
  } catch (exception: Exception) {
    Timber.tag("safeApiCall").d(exception.stackTraceToString())
    DataState.Error(exception)
  }
}

@JvmName("safeApiCallList")
suspend fun <T> safeApiCall(call: suspend () -> List<T>): DataState<List<T>> {
  val dataList = call()
  return try {
    if (dataList.isEmpty()) {
      DataState.Empty
    } else {
      DataState.Success(dataList)
    }
  } catch (exception: Exception) {
    Timber.tag("safeApiCall").d(exception.stackTraceToString())
    DataState.Error(exception)
  }
}
