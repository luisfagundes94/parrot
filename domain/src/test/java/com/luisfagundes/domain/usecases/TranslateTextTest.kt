package com.luisfagundes.domain.usecases

import com.luisfagundes.commonsTesting.TestCoroutineRule
import com.luisfagundes.domain.repositories.WordRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TranslateTextTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val repository: WordRepository = mockk()
    private lateinit var useCase: TranslateText

    @Before
    fun setup() {
        useCase = TranslateText(repository)
    }

    @Test
    fun `invoke calls repository translateWord with correct parameters`() =
        runTest {
            val params = TranslateText.Params(
                "hello",
                "en",
                "es",
            )
            coEvery { repository.translateText(params) } returns mockk()
            coEvery { repository.fetchDictionaryLookup(params) } returns mockk()

            useCase(params)

            coVerify(exactly = 1) { repository.translateText(params) }
        }
}
