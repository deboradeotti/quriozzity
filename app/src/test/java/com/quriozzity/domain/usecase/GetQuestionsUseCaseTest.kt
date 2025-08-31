package com.quriozzity.domain.usecase

import com.quriozzity.domain.model.QuestionModel
import com.quriozzity.domain.repository.QuizRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetQuestionsUseCaseTest {

    private val repository: QuizRepository = mockk()
    private lateinit var sut: GetQuestionsUseCase

    @Before
    fun setUp() {
        sut = GetQuestionsUseCase(repository)
    }

    @Test
    fun `WHEN use case is invoked THEN it should call repository and return questions`() = runTest {
        val mockQuestions = listOf(
            QuestionModel(
                question = "What is the capital of Japan?",
                correctAnswer = "Tokyo",
                options = listOf("Tokyo", "Kyoto", "Osaka")
            )
        )
        coEvery { repository.getQuestions() } returns mockQuestions

        val result = sut()

        coVerify(exactly = 1) { repository.getQuestions() }
        assertEquals(mockQuestions, result)
    }
}