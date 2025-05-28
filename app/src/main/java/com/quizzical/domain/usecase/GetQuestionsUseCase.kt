package com.quizzical.domain.usecase

import com.quizzical.domain.repository.QuizRepository
import com.quizzical.domain.model.QuestionModel

class GetQuestionsUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(): List<QuestionModel> {
        return repository.getQuestions()
    }
}