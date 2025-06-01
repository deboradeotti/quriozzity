package com.quizzical.presentation.quiz.state

import com.quizzical.domain.model.QuestionModel

data class QuizUiState(
    val quizQuestions: List<QuestionModel>,
    val isCheckAnswersButtonEnabled: Boolean = false
)