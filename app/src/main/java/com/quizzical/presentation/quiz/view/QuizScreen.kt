package com.quizzical.presentation.quiz.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quizzical.domain.model.QuestionModel
import com.quizzical.presentation.quiz.action.QuizAction
import com.quizzical.presentation.quiz.state.QuizUiState
import com.quizzical.presentation.quiz.viewmodel.QuizViewModel
import com.quizzical.ui.theme.QuizzicalTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.sendAction(QuizAction.Action.OnInit) }

    Scaffold {
        paddingValues ->
        QuizContent(uiState = uiState, modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizContent(
    uiState: QuizUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        uiState.quizQuestions.forEach { quizQuestion ->
            val question = quizQuestion.question
            val options = quizQuestion.options

            item {
                Text(
                    text = question,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    options.forEach { option ->
                        Button(onClick = { /* select */ }) {
                            Text(text = option)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizzicalTheme {
        QuizContent(mockUiState())
    }
}

private fun mockUiState(): QuizUiState {
    return QuizUiState(
        quizQuestions = listOf(
            QuestionModel(
                type = "multiple_choice",
                difficulty = "easy",
                category = "Geography",
                question = "What is the capital of France?",
                options = listOf("Paris", "London", "Berlin", "Madrid"),
                correctAnswer = "Paris",
            ),
            QuestionModel(
                type = "multiple_choice",
                difficulty = "medium",
                category = "Science",
                question = "What is the chemical symbol for water?",
                options = listOf("H2O", "CO2", "O2", "NaCl"),
                correctAnswer = "H2O",
            )
        )
    )
}