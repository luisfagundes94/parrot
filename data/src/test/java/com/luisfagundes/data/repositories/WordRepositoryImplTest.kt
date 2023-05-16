package com.luisfagundes.data.repositories

import com.luisfagundes.commonsTesting.TestCoroutineRule
import com.luisfagundes.data.local.database.ParrotDatabase
import com.luisfagundes.data.remote.services.LingueeApiService
import com.luisfagundes.domain.usecases.GetWordTranslations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WordRepositoryImplTest {

  @get:Rule
  val coroutineRule = TestCoroutineRule()

  private val lingueeApiService: LingueeApiService = mockk()
  private val database: ParrotDatabase = mockk()
  private lateinit var repository: WordRepositoryImpl

  @Before
  fun setup() {
    repository = WordRepositoryImpl(
      lingueeApiService,
      database,
    )
  }

  @Test
  fun `translateWord calls lingueeApiService fetchWordTranslations with correct parameters`() =
    coroutineRule.runTest {
      val params = GetWordTranslations.Params(
        "hello",
        "en",
        "es",
      )
      val expectedMap = mapOf(
        "query" to "hello",
        "src" to "en",
        "dst" to "es",
      )

      coEvery { lingueeApiService.fetchWordTranslations(any()) } returns mockk()

      repository.translateWord(params)

      coVerify(exactly = 1) { lingueeApiService.fetchWordTranslations(expectedMap) }
    }
}
