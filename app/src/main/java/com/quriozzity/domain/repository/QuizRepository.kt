package com.quriozzity.domain.repository

import com.quriozzity.domain.model.QuestionModel

interface QuizRepository {
    suspend fun getQuestions(): List<QuestionModel>
}