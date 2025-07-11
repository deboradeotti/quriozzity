package com.quriozzity.presentation.quiz.action

fun interface QuizAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
        data class OnClickSelectOption(val questionIndex: Int, val optionIndex: Int) : Action()
        data object OnClickCheckAnswers : Action()
        data object OnClickRestart : Action()
        data object OnClickTryAgain : Action()
    }
}