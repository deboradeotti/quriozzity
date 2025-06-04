package com.quizzical.presentation.start.action

fun interface StartAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnClickStart : Action()
        data object OnClickAbout : Action()
    }
}
