package com.quriozzity.presentation.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quriozzity.domain.usecase.GetQuestionsUseCase
import com.quriozzity.presentation.quiz.action.QuizAction
import com.quriozzity.presentation.quiz.model.QuizUiModel
import com.quriozzity.presentation.quiz.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel(), QuizAction {
    private var _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Resumed(QuizUiModel(emptyList())))
    val uiState = _uiState.asStateFlow()

    override fun sendAction(action: QuizAction.Action) {
        when (action) {
            is QuizAction.Action.OnInit -> fetchQuestions()
            QuizAction.Action.OnClickCheckAnswers -> onClickCheckAnswers()
            is QuizAction.Action.OnClickSelectOption -> onClickSelectOption(
                questionIndex = action.questionIndex,
                optionIndex = action.optionIndex
            )
            QuizAction.Action.OnClickRestart -> onClickRestart()
            QuizAction.Action.OnClickTryAgain -> onClickRestart()
        }
    }

    private fun fetchQuestions() {
        _uiState.value = QuizUiState.Loading
        viewModelScope.launch {
            try {
                val quizQuestions = getQuestionsUseCase()
                _uiState.value = QuizUiState.Resumed(QuizUiModel(quizQuestions))
            } catch (e: Exception) {
                _uiState.value = QuizUiState.Error
            }
        }
    }

    private fun onClickSelectOption(questionIndex: Int, optionIndex: Int) {
        val state = _uiState.value
        if (state is QuizUiState.Resumed) {
            val questions = state.uiModel.quizQuestions.toMutableList()
            if (questionIndex in questions.indices) {
                val question = questions[questionIndex]
                val updatedQuestion = question.copy(selectedOptionIndex = optionIndex)
                questions[questionIndex] = updatedQuestion
                _uiState.value = QuizUiState.Resumed(state.uiModel.copy(quizQuestions = questions))
                checkButtonState()
            }
        }
    }

    private fun checkButtonState() {
        val state = _uiState.value
        if (state is QuizUiState.Resumed) {
            val allAnswered = state.uiModel.quizQuestions.all { it.selectedOptionIndex != null }
            _uiState.value = QuizUiState.Resumed(
                state.uiModel.copy(isCheckAnswersButtonEnabled = allAnswered)
            )
        }
    }

    private fun onClickCheckAnswers() {
        val state = _uiState.value

        if (state is QuizUiState.Resumed && state.uiModel.isCheckAnswersButtonEnabled) {
            var score = state.uiModel.score
            state.uiModel.quizQuestions.forEach { question ->
               question.options.forEach { option ->
                   val isSelected = question.selectedOptionIndex == question.options.indexOf(option)
                    if (isSelected && option == question.correctAnswer) {
                        score += 1
                    }
               }
            }

            _uiState.value = QuizUiState.Result(
                state.uiModel.copy(
                    quizQuestions = state.uiModel.quizQuestions,
                    score = score
                )
            )
        }
    }

    private fun onClickRestart() {
        _uiState.value = QuizUiState.Resumed(QuizUiModel(emptyList()))
        fetchQuestions()
    }
}