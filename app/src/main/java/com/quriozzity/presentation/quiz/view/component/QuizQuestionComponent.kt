package com.quriozzity.presentation.quiz.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quriozzity.R
import com.quriozzity.presentation.quiz.view.utils.quizButtonColors
import com.quriozzity.ui.theme.AppTypography
import com.quriozzity.ui.theme.ColorPalette

@Composable
fun QuizQuestionComponent(
    question: String,
    options: List<String>,
    selectedOptionIndex: Int?,
    correctAnswer: String?,
    isResult: Boolean,
    onOptionClick: ((Int) -> Unit)? = null
) {
    Text(
        text = question,
        modifier = Modifier.padding(16.dp),
        fontFamily = AppTypography.InterRegular,
        color = ColorPalette.CustomBlue,
        fontSize = 16.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { index, option ->
            if (isResult) {
                val isSelected = selectedOptionIndex == index
                val isCorrect = option == correctAnswer
                Button(
                    enabled = false,
                    colors = quizButtonColors(
                        backgroundColor = when {
                            isCorrect -> ColorPalette.CustomGreen
                            isSelected -> ColorPalette.CustomRed
                            else -> ColorPalette.LightBlue
                        },
                        contentColor = ColorPalette.CustomBlue
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = option,
                            fontFamily = AppTypography.InterBold,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center),
                        )
                        if (isSelected) {
                            if (isCorrect) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    tint = ColorPalette.CustomBlue,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(start = 8.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_cancel),
                                    contentDescription = null,
                                    tint = ColorPalette.CustomBlue,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { onOptionClick?.invoke(index) },
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (selectedOptionIndex == index) ColorPalette.CustomBlue else ColorPalette.LightBlue
                    ),
                    colors = quizButtonColors(
                        backgroundColor = ColorPalette.LightBlue,
                        contentColor = ColorPalette.CustomBlue
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = option,
                        fontFamily = AppTypography.InterBold,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionComponentPreview() {
    QuizQuestionComponent(
        question = "What is the capital of France?",
        options = listOf("Berlin", "Madrid", "Paris", "Rome"),
        selectedOptionIndex = 2,
        correctAnswer = "Paris",
        isResult = true
    )
}
