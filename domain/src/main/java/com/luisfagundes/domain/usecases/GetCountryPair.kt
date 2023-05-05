package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.CountryRepository
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class GetCountryPair(
    private val repository: CountryRepository
) {
    suspend operator fun invoke() =
        repository.fetchCountryPair()
}