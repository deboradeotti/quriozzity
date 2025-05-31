package com.quizzical.presentation.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizzical.presentation.start.action.StartAction
import com.quizzical.presentation.start.effect.StartUiEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class StartViewModel(

) : ViewModel(), StartAction
{
    private val _uiEffect = MutableSharedFlow<StartUiEffect>(replay = 0, extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()


    override fun sendAction(action: StartAction.Action) {
        when (action) {
            is StartAction.Action.OnInit -> {
                // Handle initialization logic if needed
            }
            is StartAction.Action.OnClickStart -> {
                viewModelScope.launch { onClickStart() }
            }
            is StartAction.Action.OnClickInfo -> {
                // Handle info button click logic
            }
        }
    }

    private suspend fun onClickStart() {
        _uiEffect.emit(StartUiEffect.NavigateToQuiz)
    }
}