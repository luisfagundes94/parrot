package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.Country

interface CountryRepository {
    suspend fun listCountries(): List<Country>
    suspend fun getCountryPair(): Pair<Country, Country>
}