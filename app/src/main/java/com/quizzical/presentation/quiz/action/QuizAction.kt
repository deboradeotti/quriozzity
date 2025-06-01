package com.quizzical.presentation.quiz.action

fun interface QuizAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
        data class OnClickSelectOption(val questionIndex: Int, val optionIndex: Int) : Action()
        data object OnClickCheckAnswers : Action()
    }
}