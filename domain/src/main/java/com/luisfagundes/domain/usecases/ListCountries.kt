package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.CountryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ListCountries @Inject constructor(
   private val repository: CountryRepository
) {
    suspend operator fun invoke() = repository.listCountries()
}