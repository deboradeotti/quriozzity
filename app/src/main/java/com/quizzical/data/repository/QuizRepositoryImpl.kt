package com.quizzical.data.repository

import androidx.core.text.HtmlCompat
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
                question = decodeHtmlEntities(questionDTO.question),
                correctAnswer = decodeHtmlEntities(questionDTO.correctAnswer),
                options = options.map { decodeHtmlEntities(it) }
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

    private fun decodeHtmlEntities(text: String): String {
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}