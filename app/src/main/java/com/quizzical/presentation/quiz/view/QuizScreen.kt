package com.quizzical.presentation.quiz.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.material3.CircularProgressIndicator
import com.quizzical.presentation.quiz.state.QuizUiState
import com.quizzical.presentation.quiz.view.component.QuizQuestionComponent
import com.quizzical.presentation.quiz.view.utils.quizButtonColors

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
        onClickRestartQuiz = { viewModel.sendAction(QuizAction.Action.OnClickRestart) },
        onClickRetry = { viewModel.sendAction(QuizAction.Action.OnClickTryAgain) }
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
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp),
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
    onClickRestartQuiz: () -> Unit,
    onClickRetry: () -> Unit
) {
    Scaffold(
        topBar = { QuizTopBar(Modifier) },
        bottomBar = {
            when (uiState) {
                is QuizUiState.Loading -> Unit
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
                is QuizUiState.Error -> QuizErrorBottomBar(
                    onClickRetry = onClickRetry,
                    modifier = Modifier.padding(8.dp)
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
                is QuizUiState.Loading -> QuizLoadingContent()
                is QuizUiState.Resumed -> QuizResumedContent(
                    uiModel = uiState.uiModel,
                    onClickSelectOption = onClickSelectOption
                )
                is QuizUiState.Result -> {
                    QuizResultContent(
                        uiModel = uiState.uiModel
                    )
                }
                is QuizUiState.Error -> QuizErrorContent()
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
            QuizQuestionComponent(
                question = quizQuestion.question,
                options = quizQuestion.options,
                selectedOptionIndex = quizQuestion.selectedOptionIndex,
                correctAnswer = quizQuestion.correctAnswer,
                isResult = false,
                onOptionClick = { optionIndex ->
                    onClickSelectOption(questionIndex, optionIndex)
                }
            )
        }
    }
}

@Composable
fun QuizResultContent(
    uiModel: QuizUiModel
) {
    LazyColumn {
        itemsIndexed(uiModel.quizQuestions, key = { index, _ -> index }) { _, quizQuestion ->
            QuizQuestionComponent(
                question = quizQuestion.question,
                options = quizQuestion.options,
                selectedOptionIndex = quizQuestion.selectedOptionIndex,
                correctAnswer = quizQuestion.correctAnswer,
                isResult = true
            )
        }
    }
}

@Composable
fun QuizLoadingContent() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = ColorPalette.CustomBlue
        )
    }
}

@Composable
fun QuizErrorContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.quiz_screen_error_message),
            fontFamily = AppTypography.InterRegular,
            fontSize = 16.sp,
            color = ColorPalette.CustomBlue,
            textAlign = TextAlign.Center
        )
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
            )
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
fun QuizErrorBottomBar(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = onClickRetry,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = quizButtonColors(
                backgroundColor = ColorPalette.CustomBlue,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = stringResource(R.string.quiz_screen_try_again_button),
                fontFamily = AppTypography.InterBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenResumedPreview() {
    QuizzicalTheme {
        QuizContent(
            QuizUiState.Resumed(mockUiModel()),
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ },
            onClickRestartQuiz = { /* No action needed for preview */ },
            onClickRetry = { /* No action needed for preview */ }
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
            onClickRestartQuiz = { /* No action needed for preview */ },
            onClickRetry = { /* No action needed for preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenLoadingPreview() {
    QuizzicalTheme {
        QuizContent(
            QuizUiState.Loading,
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ },
            onClickRestartQuiz = { /* No action needed for preview */ },
            onClickRetry = { /* No action needed for preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenErrorPreview() {
    QuizzicalTheme {
        QuizContent(
            QuizUiState.Error,
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ },
            onClickRestartQuiz = { /* No action needed for preview */ },
            onClickRetry = { /* No action needed for preview */ }
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