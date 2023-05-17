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

class GetWordTranslationsTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val repository: WordRepository = mockk()
    private lateinit var useCase: GetWordTranslations

    @Before
    fun setup() {
        useCase = GetWordTranslations(repository)
    }

    @Test
    fun `invoke calls repository translateWord with correct parameters`() =
        runTest {
            val params = GetWordTranslations.Params(
                "hello",
                "en",
                "es",
            )

            coEvery { repository.translateWord(params) } returns mockk()

            useCase(params)

            coVerify(exactly = 1) { repository.translateWord(params) }
        }
}
