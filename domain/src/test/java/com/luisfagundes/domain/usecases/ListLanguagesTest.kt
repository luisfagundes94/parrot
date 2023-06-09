package com.luisfagundes.domain.usecases

import com.luisfagundes.commonsTesting.TestCoroutineRule
import com.luisfagundes.domain.modelFactory.LanguageFactory
import com.luisfagundes.domain.repositories.LanguageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListLanguagesTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val repository: LanguageRepository = mockk()
    private lateinit var useCase: ListLanguages

    @Before
    fun setup() {
        useCase = ListLanguages(repository)
    }

    @Test
    fun `invoke calls repository listLanguages`() = runTest {
        val languages = LanguageFactory.languages
        coEvery { repository.listLanguages() } returns languages

        useCase()

        coVerify(exactly = 1) { repository.listLanguages() }
    }
}
