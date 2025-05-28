package com.quizzical.domain.repository

import com.quizzical.domain.model.QuestionModel

interface QuizRepository {
    suspend fun getQuestions(): List<QuestionModel>
}