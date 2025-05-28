package com.quizzical.di

import com.quizzical.data.datasource.api.ApiClient
import com.quizzical.data.datasource.api.ApiService
import com.quizzical.domain.repository.QuizRepository
import com.quizzical.data.repository.QuizRepositoryImpl
import com.quizzical.domain.usecase.GetQuestionsUseCase
import com.quizzical.presentation.quiz.viewmodel.QuizViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { ApiClient.getRetrofitInstance() }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    single<QuizRepository> { QuizRepositoryImpl(get()) }
    single { GetQuestionsUseCase(get()) }
    viewModelOf(::QuizViewModel)
}