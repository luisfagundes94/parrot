package com.luisfagundes.framework.network

fun <T> List<DataState<List<T>>>.merge(): DataState<List<T>> {
    val mergedState = mutableListOf<T>()
    forEach { state ->
        when (state) {
            is DataState.Success -> mergedState.addAll(state.result)
            is DataState.Empty -> {}
            is DataState.Error -> return@merge DataState.Error(state.error)
        }
    }
    return if (mergedState.isNotEmpty()) {
        DataState.Success(mergedState)
    } else {
        DataState.Empty
    }
}
