package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.WordRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetAllSavedWords @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke() = repository.getAllSavedWords()
}