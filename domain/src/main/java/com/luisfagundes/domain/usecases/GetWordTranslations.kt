package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.repositories.WordRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetWordTranslations @Inject constructor(
    private val repository: WordRepository
) {
    data class Params(
        val text: String,
        val sourceLanguage: String,
        val destLanguage: String
    )

    suspend operator fun invoke(params: Params) =
        repository.translateWord(params)
}