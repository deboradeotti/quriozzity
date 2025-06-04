package com.quriozzity.di

import com.quriozzity.data.datasource.api.ApiClient
import com.quriozzity.data.datasource.api.ApiService
import com.quriozzity.domain.repository.QuizRepository
import com.quriozzity.data.repository.QuizRepositoryImpl
import com.quriozzity.domain.usecase.GetQuestionsUseCase
import com.quriozzity.presentation.quiz.viewmodel.QuizViewModel
import com.quriozzity.presentation.start.viewmodel.StartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { ApiClient.getRetrofitInstance() }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    single<QuizRepository> { QuizRepositoryImpl(get()) }
    single { GetQuestionsUseCase(get()) }
    viewModelOf(::QuizViewModel)
    viewModelOf(::StartViewModel)
}