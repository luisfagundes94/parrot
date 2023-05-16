package com.luisfagundes.data.repositories

import android.content.Context
import android.content.res.AssetManager
import com.luisfagundes.commons_testing.TestCoroutineRule
import com.luisfagundes.data.local.datastore.LanguageDataStore
import com.luisfagundes.domain.modelFactory.LanguageFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream

@OptIn(ExperimentalCoroutinesApi::class)
class LanguageRepositoryImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val appContext: Context = mockk()
    private val languageDataStore: LanguageDataStore = mockk()
    private lateinit var repository: LanguageRepositoryImpl

    @Before
    fun setup() {
        val mockAssets: AssetManager = mockk()
        val mockInputStream: InputStream = ByteArrayInputStream(
            LanguageFactory.languagesInJsonString.toByteArray(),
        )

        every { appContext.assets } returns mockAssets
        every { mockAssets.open(any()) } returns mockInputStream

        repository = LanguageRepositoryImpl(appContext, languageDataStore)
    }

    @Test
    fun `listLanguages returns the correct list of languages`() = coroutineRule.runTest {
        val languages = LanguageFactory.languages

        repository.languagesMap.putAll(languages.map { it.id to it })

        val result = repository.listLanguages()

        assert(result == languages)
    }

    @Test
    fun `fetchLanguagePair calls languageDataStore for source and destination ids`() =
        coroutineRule.runTest {
            val sourceLanguage = LanguageFactory.languages.first()
            val destLanguage = LanguageFactory.languages.last()

            repository.languagesMap.putAll(
                mapOf(
                    sourceLanguage.id to sourceLanguage,
                    destLanguage.id to destLanguage,
                ),
            )

            coEvery { languageDataStore.sourceLanguageId } returns flowOf(sourceLanguage.id)
            coEvery { languageDataStore.destLanguageId } returns flowOf(destLanguage.id)

            repository.fetchLanguagePair()

            coVerify { languageDataStore.sourceLanguageId }
            coVerify { languageDataStore.destLanguageId }
        }

    @Test
    fun `updateLanguage calls languageDataStore to update the language ids`() =
        coroutineRule.runTest {
            val id = LanguageFactory.languages.first().id
            val isSourceLanguage = true

            coEvery { languageDataStore.updateSourceLanguageId(id) } returns Unit

            repository.updateLanguage(id, isSourceLanguage)

            coVerify { languageDataStore.updateSourceLanguageId(id) }
        }
}
