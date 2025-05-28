package com.quizzical.presentation.quiz.action

fun interface QuizAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
    }
}