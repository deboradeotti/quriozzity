package com.quriozzity.presentation.quiz.state

import com.quriozzity.presentation.quiz.model.QuizUiModel

sealed class QuizUiState {
    data object Loading : QuizUiState()
    data class Resumed(var uiModel: QuizUiModel) : QuizUiState()
    data class Result(var uiModel: QuizUiModel) : QuizUiState()
    data object Error : QuizUiState()
}