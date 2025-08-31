package com.quriozzity.data.repository

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.quriozzity.data.datasource.api.ApiService
import com.quriozzity.data.datasource.dto.QuestionDTO
import com.quriozzity.data.datasource.dto.QuizResponseDTO
import com.quriozzity.domain.model.QuestionModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuizRepositoryImplTest {

    private val apiService: ApiService = mockk()
    private lateinit var sut: QuizRepositoryImpl

    @Before
    fun setUp() {
        mockkStatic(HtmlCompat::class)
        every { HtmlCompat.fromHtml(any(), any()) } answers {
            val sourceString = firstArg<String>()
            val mockedSpanned = mockk<Spanned>()
            every { mockedSpanned.toString() } returns sourceString
            mockedSpanned
        }

        sut = QuizRepositoryImpl(apiService)
    }

    @Test
    fun `GIVEN successful API response WHEN getQuestions is called THEN it returns mapped questions`() =
        runTest {
            val questionDTO = QuestionDTO(
                question = "What does &quot;CPU&quot; stand for?",
                correctAnswer = "Central Processing Unit",
                incorrectAnswers = listOf(
                    "Central Process Unit",
                    "Computer Personal Unit",
                    "Central Processor Unit"
                ),
                type = "multiple",
                difficulty = "easy",
                category = "Science: Computers"
            )
            val response = QuizResponseDTO(
                results = listOf(questionDTO),
                responseCode = 1
            )

            coEvery { apiService.getQuestions() } returns response

            val result: List<QuestionModel> = sut.getQuestions()

            assertEquals(1, result.size)
            assertEquals("What does &quot;CPU&quot; stand for?", result[0].question)
            assertEquals("Central Processing Unit", result[0].correctAnswer)
            assertEquals(4, result[0].options.size)
            assertTrue(result[0].options.contains("Central Processing Unit"))
        }
}