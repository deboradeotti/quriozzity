package com.quizzical.domain.model

data class QuestionModel(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correctAnswer: String,
    val options: List<String>
)