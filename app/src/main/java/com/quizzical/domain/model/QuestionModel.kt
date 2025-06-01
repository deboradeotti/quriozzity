package com.quizzical.domain.model

data class QuestionModel(
    val question: String,
    val correctAnswer: String,
    val options: List<String>,
    var selectedOptionIndex: Int? = null
)