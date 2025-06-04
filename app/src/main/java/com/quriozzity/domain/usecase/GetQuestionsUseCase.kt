package com.quriozzity.domain.usecase

import com.quriozzity.domain.repository.QuizRepository
import com.quriozzity.domain.model.QuestionModel

class GetQuestionsUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(): List<QuestionModel> {
        return repository.getQuestions()
    }
}