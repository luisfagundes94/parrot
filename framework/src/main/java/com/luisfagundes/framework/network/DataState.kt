package com.luisfagundes.framework.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

sealed class DataState<out T> {
    data class Success<out T>(val result: T) : DataState<T>()
    data class Error(val error: Throwable) : DataState<Nothing>()

    object Empty : DataState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$result]"
            is Error -> "Error[exception=$error]"
            is Empty -> "Empty"
        }
    }

    inline fun <R : Any> map(transform: (T) -> R): DataState<R> {
        return when (this) {
            is Error -> Error(this.error)
            is Success -> Success(transform(this.result))
            is Empty -> Empty
        }
    }

    suspend inline fun <R : Any> suspendMap(
        crossinline transform: suspend (T) -> R,
    ): DataState<R> {
        return when (this) {
            is Error -> Error(this.error)
            is Success -> Success(transform(this.result))
            is Empty -> Empty
        }
    }

    // Get Result or error
    fun getResultOrThrow(): T {
        return when (this) {
            is Success -> this.result
            is Error -> throw this.error
            is Empty -> error("Empty state has no result")
        }
    }
}

fun <T : Any> Flow<T>.asStatefulData(): Flow<DataState<T>> = wrapWithStatefulData()
    .catch {
        emit(DataState.Error(it))
    }

fun <T : Any> Flow<T>.wrapWithStatefulData(): Flow<DataState<T>> = transform {
    return@transform emit(DataState.Success(it))
}

inline fun <T : Any, R : Any> Flow<DataState<T>>.mapState(
    crossinline transform: suspend (value: T) -> R,
): Flow<DataState<R>> =
    transform { value ->
        return@transform emit(value.suspendMap(transform))
    }

inline fun <T : Any> Flow<DataState<T>>.onSuccessState(
    crossinline action: suspend (value: T) -> Unit,
): Flow<DataState<T>> =
    onEach {
        if (it is DataState.Success) action(it.result)
    }

inline fun <T : Any> Flow<DataState<T>>.onErrorState(
    crossinline action: suspend (error: Throwable) -> Unit,
): Flow<DataState<T>> =
    onEach {
        if (it is DataState.Error) action(it.error)
    }
