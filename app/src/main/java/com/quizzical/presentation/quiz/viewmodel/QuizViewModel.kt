package com.quizzical.presentation.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizzical.domain.usecase.GetQuestionsUseCase
import com.quizzical.presentation.quiz.action.QuizAction
import com.quizzical.presentation.quiz.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel(), QuizAction {
    private val _uiState = MutableStateFlow(QuizUiState(emptyList()))
    val uiState = _uiState.asStateFlow()

    override fun sendAction(action: QuizAction.Action) {
        when (action) {
            is QuizAction.Action.OnInit -> fetchQuestions()
            QuizAction.Action.OnClickCheckAnswers -> TODO()
            is QuizAction.Action.OnClickSelectOption -> onClickSelectOption(
                questionIndex = action.questionIndex,
                optionIndex = action.optionIndex
            )
        }
    }

    private fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val quizQuestions = getQuestionsUseCase()
                _uiState.value = QuizUiState(quizQuestions)
            } catch (e: Exception) {
                // Handle error, e.g., log it or show a message to the user
                _uiState.value = QuizUiState(emptyList()) // Reset to empty list on error
            }
        }
    }

    private fun onClickSelectOption(questionIndex: Int, optionIndex: Int) {
        val currentQuestions = _uiState.value.quizQuestions.toMutableList()
        if (questionIndex in currentQuestions.indices) {
            val question = currentQuestions[questionIndex]
            val updatedQuestion = question.copy(selectedOptionIndex = optionIndex)
            currentQuestions[questionIndex] = updatedQuestion
            _uiState.value = QuizUiState(currentQuestions)
        }

        checkButtonState()
    }

    private fun checkButtonState() {
        val allAnswered = _uiState.value.quizQuestions.all { it.selectedOptionIndex != null }
        _uiState.value = _uiState.value.copy(
            isCheckAnswersButtonEnabled = allAnswered
        )
    }
}