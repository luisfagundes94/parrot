package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.LanguageRepository
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class GetLanguagePair(
  private val repository: LanguageRepository,
) {
  suspend operator fun invoke() = repository.fetchLanguagePair()
}
