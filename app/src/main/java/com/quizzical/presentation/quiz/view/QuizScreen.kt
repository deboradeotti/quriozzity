package com.quizzical.presentation.quiz.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quizzical.R
import com.quizzical.domain.model.QuestionModel
import com.quizzical.presentation.quiz.action.QuizAction
import com.quizzical.presentation.quiz.model.QuizUiModel
import com.quizzical.presentation.quiz.viewmodel.QuizViewModel
import com.quizzical.ui.theme.AppTypography
import com.quizzical.ui.theme.ColorPalette
import com.quizzical.ui.theme.QuizzicalTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.quizzical.presentation.quiz.state.QuizUiState

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.sendAction(QuizAction.Action.OnInit) }

    QuizContent(
        uiState = uiState,
        onClickSelectOption = {
            questionIndex, optionIndex ->
            viewModel.sendAction(QuizAction.Action.OnClickSelectOption(
                questionIndex = questionIndex,
                optionIndex = optionIndex
            ))},
        onClickCheckAnswers = { viewModel.sendAction(QuizAction.Action.OnClickCheckAnswers) },
        onClickRestartQuiz = { viewModel.sendAction(QuizAction.Action.OnClickRestart) }
    )
}

@Composable
fun QuizTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = modifier.padding(top = 32.dp, bottom = 8.dp),
            fontFamily = AppTypography.Kavoon,
            color = ColorPalette.CustomBlue,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
fun QuizContent(
    uiState: QuizUiState,
    onClickSelectOption: (Int, Int) -> Unit,
    onClickCheckAnswers: () -> Unit,
    onClickRestartQuiz: () -> Unit
) {
    Scaffold(
        topBar = { QuizTopBar(Modifier) },
        bottomBar = {
            when (uiState) {
                is QuizUiState.Resumed -> QuizResumedBottomBar(
                    modifier = Modifier.padding(8.dp),
                    onClickCheckAnswers = onClickCheckAnswers,
                    isCheckAnswersButtonEnabled = uiState.uiModel.isCheckAnswersButtonEnabled
                )

                is QuizUiState.Result -> QuizResultBottomBar(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    onClickRestartQuiz = onClickRestartQuiz,
                    score = uiState.uiModel.score
                )
            }
        },
        containerColor = ColorPalette.CustomYellow
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is QuizUiState.Resumed -> QuizResumedContent(
                    uiModel = uiState.uiModel,
                    onClickSelectOption = onClickSelectOption
                )
                is QuizUiState.Result -> {
                    QuizResultContent(
                        uiModel = uiState.uiModel
                    )
                }
            }
        }
    }
}

@Composable
fun QuizResumedContent(
    uiModel: QuizUiModel,
    onClickSelectOption: (Int, Int) -> Unit
) {
    LazyColumn {
        itemsIndexed(uiModel.quizQuestions, key = { index, _ -> index }) { questionIndex, quizQuestion ->
            Text(
                text = quizQuestion.question,
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
                quizQuestion.options.forEachIndexed { optionIndex, option ->
                    val isSelected = quizQuestion.selectedOptionIndex == optionIndex
                    OutlinedButton(
                        onClick = { onClickSelectOption(questionIndex, optionIndex) },
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (isSelected) ColorPalette.CustomBlue else ColorPalette.LightBlue
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
}

@Composable
fun QuizResultContent(
    uiModel: QuizUiModel
) {
    LazyColumn {
        itemsIndexed(uiModel.quizQuestions, key = { index, _ -> index }) { _, quizQuestion ->
            Text(
                text = quizQuestion.question,
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
                quizQuestion.options.forEachIndexed { _, option ->
                    val isSelected = quizQuestion.selectedOptionIndex != null && quizQuestion.options.indexOf(option) == quizQuestion.selectedOptionIndex
                    val isCorrect = option == quizQuestion.correctAnswer
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
                        onClick = { /* No action needed for result display */ }
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
                }
            }
        }
    }
}

@Composable
fun QuizResumedBottomBar(
    modifier: Modifier = Modifier,
    onClickCheckAnswers: () -> Unit,
    isCheckAnswersButtonEnabled: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = onClickCheckAnswers,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = quizButtonColors(
                backgroundColor =
                    if (isCheckAnswersButtonEnabled)
                        ColorPalette.CustomBlue else Color.Gray,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = stringResource(R.string.quiz_screen_check_button),
                fontFamily = AppTypography.InterBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun QuizResultBottomBar(
    modifier: Modifier = Modifier,
    onClickRestartQuiz: () -> Unit,
    score: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "You scored $score/5!",
            fontFamily = AppTypography.InterBold,
            color = ColorPalette.CustomBlue,
            fontSize = 20.sp
        )
        Button(
            onClick = onClickRestartQuiz,
            colors = quizButtonColors(
                backgroundColor = ColorPalette.CustomBlue,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = stringResource(R.string.quiz_screen_restart_button),
                fontFamily = AppTypography.InterBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun QuizScreenResumedPreview() {
    QuizzicalTheme {
        QuizContent(
            QuizUiState.Resumed(mockUiModel()),
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ },
            onClickRestartQuiz = { /* No action needed for preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenResultPreview() {
    QuizzicalTheme {
        QuizContent(
            QuizUiState.Result(mockUiModel()),
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ },
            onClickRestartQuiz = { /* No action needed for preview */ }
        )
    }
}

private fun mockUiModel(): QuizUiModel {
    return QuizUiModel(
        quizQuestions = listOf(
            QuestionModel(
                question = "What is the capital of France?",
                options = listOf("Paris", "London", "Berlin", "Madrid"),
                correctAnswer = "Paris",
                selectedOptionIndex = 2
            ),
            QuestionModel(
                question = "What is the chemical symbol for water?",
                options = listOf("H2O", "CO2", "O2", "NaCl"),
                correctAnswer = "H2O",
                selectedOptionIndex = 0
            )
        )
    )
}