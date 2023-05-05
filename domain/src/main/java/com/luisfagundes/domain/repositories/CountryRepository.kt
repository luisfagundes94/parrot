package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.Country

interface CountryRepository {
    suspend fun fetchCountries(): List<Country>
    suspend fun fetchCountryPair(): Pair<Country, Country>
}