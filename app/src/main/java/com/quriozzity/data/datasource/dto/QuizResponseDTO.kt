package com.quriozzity.data.datasource.dto

import com.google.gson.annotations.SerializedName

data class QuizResponseDTO(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("results") val results: List<QuestionDTO>
)