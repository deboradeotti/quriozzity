package com.quriozzity.presentation.start.effect

sealed class StartUiEffect {
    data object NavigateToQuiz : StartUiEffect()
    data object NavigateToAbout : StartUiEffect()
}