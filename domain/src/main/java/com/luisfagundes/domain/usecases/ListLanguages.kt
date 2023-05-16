package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.LanguageRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ListLanguages @Inject constructor(
    private val repository: LanguageRepository,
) {
    suspend operator fun invoke() = repository.listLanguages()
}
