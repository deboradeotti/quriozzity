package com.quizzical.data.repository

import com.quizzical.data.datasource.api.ApiService
import com.quizzical.domain.repository.QuizRepository
import com.quizzical.domain.model.QuestionModel

class QuizRepositoryImpl(
    private val apiService: ApiService
) : QuizRepository {
    override suspend fun getQuestions(): List<QuestionModel> {
        val response = apiService.getQuestions()

        return response.results.map { questionDTO ->
            val options = getShuffledOptions(
                questionDTO.correctAnswer,
                questionDTO.incorrectAnswers
            )

            QuestionModel(
                question = questionDTO.question,
                correctAnswer = questionDTO.correctAnswer,
                options = options
            )
        }
    }

    private fun getShuffledOptions(
        correctOption: String,
        incorrectOptions: List<String>
    ): List<String> {
        val options = incorrectOptions.toMutableList()
        options.add(correctOption)
        options.shuffle()
        return options
    }
}