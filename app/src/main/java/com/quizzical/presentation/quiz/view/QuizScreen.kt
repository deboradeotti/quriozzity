package com.quizzical.presentation.quiz.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.quizzical.presentation.quiz.state.QuizUiState
import com.quizzical.presentation.quiz.viewmodel.QuizViewModel
import com.quizzical.ui.theme.AppTypography
import com.quizzical.ui.theme.ColorPalette
import com.quizzical.ui.theme.QuizzicalTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.itemsIndexed

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
        onClickCheckAnswers = { viewModel.sendAction(QuizAction.Action.OnClickCheckAnswers) }
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
    modifier: Modifier = Modifier,
    onClickSelectOption: (Int, Int) -> Unit,
    onClickCheckAnswers: () -> Unit
) {
    Scaffold(
        topBar = { QuizTopBar(Modifier) },
        bottomBar = { QuizBottomBar(Modifier, onClickCheckAnswers, uiState.isCheckAnswersButtonEnabled) },
        containerColor = ColorPalette.CustomYellow
    ) { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            itemsIndexed(uiState.quizQuestions, key = { index, _ -> index }) { questionIndex, quizQuestion ->
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
                                width = if (isSelected) 1.5.dp else 0.dp,
                                color = if (isSelected) ColorPalette.CustomBlue else ColorPalette.LightBlue
                            ),
                            colors = ButtonColors(
                                containerColor = ColorPalette.LightBlue,
                                contentColor = ColorPalette.CustomBlue,
                                disabledContainerColor = ColorPalette.LightBlue,
                                disabledContentColor = ColorPalette.CustomBlue
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
}

@Composable
fun QuizBottomBar(
    modifier: Modifier = Modifier,
    onClickCheckAnswers: () -> Unit,
    isCheckAnswersButtonEnabled: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(
            onClick = { onClickCheckAnswers },
            modifier = modifier.padding(16.dp).fillMaxWidth(),
            colors = ButtonColors(
                containerColor = if (isCheckAnswersButtonEnabled) ColorPalette.CustomBlue else Color.Gray,
                contentColor = Color.White,
                disabledContainerColor = ColorPalette.CustomBlue,
                disabledContentColor = Color.White
            )
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

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizzicalTheme {
        QuizContent(
            mockUiState(),
            onClickSelectOption = {_, _ -> /* No action needed for preview */ },
            onClickCheckAnswers = { /* No action needed for preview */ }
        )
    }
}

private fun mockUiState(): QuizUiState {
    return QuizUiState(
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
            )
        )
    )
}