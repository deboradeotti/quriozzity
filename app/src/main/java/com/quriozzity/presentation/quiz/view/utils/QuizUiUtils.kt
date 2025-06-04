package com.quriozzity.presentation.quiz.view.utils

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.quriozzity.ui.theme.ColorPalette

@Composable
fun quizButtonColors(
    backgroundColor: Color,
    contentColor: Color = ColorPalette.CustomBlue
): ButtonColors = ButtonColors(
    containerColor = backgroundColor,
    contentColor = contentColor,
    disabledContainerColor = backgroundColor,
    disabledContentColor = contentColor
)