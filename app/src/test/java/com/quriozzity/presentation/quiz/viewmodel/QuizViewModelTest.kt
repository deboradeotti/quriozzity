package com.quriozzity.presentation.quiz.viewmodel

import com.quriozzity.domain.model.QuestionModel
import com.quriozzity.domain.usecase.GetQuestionsUseCase
import com.quriozzity.presentation.quiz.action.QuizAction
import com.quriozzity.presentation.quiz.state.QuizUiState
import com.quriozzity.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getQuestionsUseCase: GetQuestionsUseCase = mockk()
    private lateinit var sut: QuizViewModel

    @Before
    fun setUp() {
        sut = QuizViewModel(getQuestionsUseCase)
    }

    @Test
    fun `GIVEN use case success WHEN OnInit is sent THEN state transitions to Resumed with questions`() = runTest {
        val mockQuestions = listOf(
            QuestionModel("Question 1?", "A", listOf("A", "B"))
        )
        coEvery { getQuestionsUseCase() } returns mockQuestions

        sut.sendAction(QuizAction.Action.OnInit)

        val finalState = sut.uiState.value
        assertTrue(finalState is QuizUiState.Resumed)
        assertEquals(mockQuestions, (finalState as QuizUiState.Resumed).uiModel.quizQuestions)
        assertFalse(finalState.uiModel.isCheckAnswersButtonEnabled)
    }

    @Test
    fun `GIVEN use case failure WHEN OnInit is sent THEN state transitions to Error`() = runTest {
        coEvery { getQuestionsUseCase() } throws Exception("Network error")

        sut.sendAction(QuizAction.Action.OnInit)

        val finalState = sut.uiState.value
        assertTrue(finalState is QuizUiState.Error)
    }

    @Test
    fun `GIVEN Resumed state WHEN OnClickSelectOption is sent THEN state updates selected option`() = runTest {
        val mockQuestions = listOf(
            QuestionModel("Question 1?", "A", listOf("A", "B"), selectedOptionIndex = null)
        )
        coEvery { getQuestionsUseCase() } returns mockQuestions
        sut.sendAction(QuizAction.Action.OnInit)
        val initialState = sut.uiState.first { it is QuizUiState.Resumed }
        assertTrue(initialState is QuizUiState.Resumed)
        assertEquals(null, (initialState as QuizUiState.Resumed).uiModel.quizQuestions[0].selectedOptionIndex)

        sut.sendAction(QuizAction.Action.OnClickSelectOption(questionIndex = 0, optionIndex = 1))

        val finalState = sut.uiState.value
        assertTrue(finalState is QuizUiState.Resumed)
        val updatedQuestion = (finalState as QuizUiState.Resumed).uiModel.quizQuestions[0]
        assertEquals(1, updatedQuestion.selectedOptionIndex)
        assertTrue(finalState.uiModel.isCheckAnswersButtonEnabled)
    }
}