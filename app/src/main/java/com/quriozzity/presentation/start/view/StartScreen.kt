package com.quriozzity.presentation.start.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quriozzity.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.quriozzity.presentation.start.viewmodel.StartViewModel
import com.quriozzity.presentation.start.action.StartAction
import com.quriozzity.presentation.start.effect.StartUiEffect
import com.quriozzity.ui.theme.AppTypography.InterMedium
import com.quriozzity.ui.theme.AppTypography.InterRegular
import com.quriozzity.ui.theme.AppTypography.Kavoon
import com.quriozzity.ui.theme.ColorPalette.CustomBlue
import com.quriozzity.ui.theme.ColorPalette.CustomYellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    viewModel: StartViewModel = koinViewModel(),
    onClickStart: () -> Unit,
    onClickAbout: () -> Unit
) {
    Effects(viewModel, onClickStart, onClickAbout)

    StartContent(
        modifier.fillMaxSize(),
        onClickStart = { viewModel.sendAction(StartAction.Action.OnClickStart) },
        onClickAbout = { viewModel.sendAction(StartAction.Action.OnClickAbout) }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartContent(
    modifier: Modifier = Modifier,
    onClickStart: () -> Unit,
    onClickAbout: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CustomYellow)
    ) {
        Image(
            painter = painterResource(R.drawable.green_blob),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(600.dp)
                .offset(y = (-200).dp)
                .zIndex(0f)
        )
        Image(
            painter = painterResource(R.drawable.red_blob),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(500.dp)
                .offset(y = (200).dp)
                .zIndex(0f)
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { StartTopBar(onClickAbout) },
            modifier = Modifier.zIndex(1f)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = Kavoon,
                            fontSize = 56.sp
                        ),
                        color = CustomBlue,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.start_screen_welcome_message),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = InterRegular,
                            fontSize = 16.sp
                        ),
                        color = CustomBlue,
                        modifier = Modifier.padding(bottom = 64.dp)
                    )
                    Button(
                        onClick = onClickStart,
                        colors = ButtonColors(
                            containerColor = CustomBlue,
                            contentColor = Color.White,
                            disabledContainerColor = CustomBlue,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.start_screen_button),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = InterMedium,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StartTopBar(
    onClickAbout: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onClickAbout
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(R.string.start_screen_info_button_description),
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun Effects(
    viewModel: StartViewModel,
    onClickStart: () -> Unit,
    onClickAbout: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                is StartUiEffect.NavigateToQuiz -> onClickStart()
                StartUiEffect.NavigateToAbout -> onClickAbout()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartContent(
        modifier = Modifier.fillMaxSize(),
        onClickStart = {},
        onClickAbout = {}
    )
}