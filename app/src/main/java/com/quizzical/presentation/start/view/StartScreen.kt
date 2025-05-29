package com.quizzical.presentation.start.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quizzical.R
import androidx.compose.material3.MaterialTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onClickStart: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(R.string.start_screen_welcome_message),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 64.dp)
            )
            Button(onClick = onClickStart) {
                Text(
                    text = stringResource(R.string.start_screen_button),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    StartScreen(
        onClickStart = {}
    )
}