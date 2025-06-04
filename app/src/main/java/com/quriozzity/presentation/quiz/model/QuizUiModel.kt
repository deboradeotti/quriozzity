package com.quriozzity.presentation.quiz.model

import com.quriozzity.domain.model.QuestionModel

data class QuizUiModel(
    val quizQuestions: List<QuestionModel>,
    val isCheckAnswersButtonEnabled: Boolean = false,
    val score: Int = 0
)