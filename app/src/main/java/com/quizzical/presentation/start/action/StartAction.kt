package com.quizzical.presentation.start.action

fun interface StartAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
        data object OnClickStart : Action()
        data object OnClickInfo : Action()
    }
}
