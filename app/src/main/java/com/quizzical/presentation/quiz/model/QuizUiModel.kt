package com.quizzical.presentation.quiz.model

import com.quizzical.domain.model.QuestionModel

data class QuizUiModel(
    val quizQuestions: List<QuestionModel>,
    val isCheckAnswersButtonEnabled: Boolean = false,
    val score: Int = 0
)