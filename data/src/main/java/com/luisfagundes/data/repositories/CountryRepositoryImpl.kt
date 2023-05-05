package com.luisfagundes.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.luisfagundes.data.store.CountryDataStore
import com.luisfagundes.data.utils.getJsonDataFromAsset
import com.luisfagundes.domain.models.Country
import com.luisfagundes.domain.repositories.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher

class CountryRepositoryImpl(
    private val appContext: Context,
    private val countryDataStore: CountryDataStore
): CountryRepository {

    override suspend fun fetchCountries(): List<Country> = runBlocking(Dispatchers.IO) {
        val jsonFile = getJsonDataFromAsset(appContext, "countries.json")
        val gson = Gson()
        val data = gson.fromJson(jsonFile, Array<Country>::class.java)
        return@runBlocking data.toList()
    }

    override suspend fun fetchCountryPair(): Pair<Country, Country> {
        val sourceCountry = fetchCountryById(countryDataStore.sourceCountryId.first())
        val destCountry = fetchCountryById(countryDataStore.destCountryId.first())
        return Pair(sourceCountry, destCountry)
    }

    private suspend fun fetchCountryById(id: String): Country = runBlocking(Dispatchers.IO) {
        val countries = fetchCountries()
        return@runBlocking countries.first { it.id == id }
    }
}