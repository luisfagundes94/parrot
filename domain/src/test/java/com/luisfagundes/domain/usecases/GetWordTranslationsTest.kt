package com.luisfagundes.domain.usecases

import com.luisfagundes.commons_testing.TestCoroutineRule
import com.luisfagundes.domain.repositories.WordRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetWordTranslationsTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val repository: WordRepository = mockk()
    private lateinit var useCase: GetWordTranslations

    @Before
    fun setup() {
        useCase = GetWordTranslations(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `invoke calls repository translateWord with correct parameters`() =
        coroutineRule.runTest {
            val params = GetWordTranslations.Params(
                "hello",
                "en",
                "es"
            )

            coEvery { repository.translateWord(params) } returns mockk()

            useCase(params)

            coVerify(exactly = 1) { repository.translateWord(params) }
        }
}
