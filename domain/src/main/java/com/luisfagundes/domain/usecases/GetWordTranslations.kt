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
        repository.translateWord(
            mapOf(
                QUERY to params.text,
                SOURCE_LANGUAGE to params.sourceLanguage,
                DEST_LANGUAGE to params.destLanguage
            )
        )

    private companion object {
        const val QUERY = "query"
        const val SOURCE_LANGUAGE = "src"
        const val DEST_LANGUAGE = "dst"
    }
}