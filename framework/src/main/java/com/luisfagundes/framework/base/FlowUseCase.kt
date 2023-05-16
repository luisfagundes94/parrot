package com.luisfagundes.framework.base

import com.luisfagundes.framework.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class FlowUseCase<in P, R> {

  protected abstract suspend fun execute(params: P): DataState<R>

  operator fun invoke(params: P): Flow<DataState<R>> = flow {
    emit(execute(params))
  }.catch { throwable ->
    emit(DataState.Error(throwable))
  }
}
