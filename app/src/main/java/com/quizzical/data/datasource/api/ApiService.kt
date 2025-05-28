package com.quizzical.data.datasource.api

import com.quizzical.data.datasource.dto.QuizResponseDTO
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api.php?amount=10&type=multiple")
    suspend fun getQuestions(): QuizResponseDTO
}