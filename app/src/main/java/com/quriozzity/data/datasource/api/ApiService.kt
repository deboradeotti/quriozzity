package com.quriozzity.data.datasource.api

import com.quriozzity.data.datasource.dto.QuizResponseDTO
import retrofit2.http.GET

interface ApiService {
    @GET("api.php?amount=5&type=multiple")
    suspend fun getQuestions(): QuizResponseDTO
}