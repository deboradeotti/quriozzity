package com.quizzical.presentation.about.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.quizzical.R
import com.quizzical.ui.theme.AppTypography
import com.quizzical.ui.theme.ColorPalette
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = { AboutTopBar(onClickBack = onClickBack) },
        content = {
            paddingValues ->
            AboutContent(
                modifier = modifier
                    .padding(paddingValues)
            )
        },
        containerColor = ColorPalette.CustomYellow
    )
}

@Composable
fun AboutTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 40.dp, bottom = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = modifier
                .clickable { onClickBack() }
                .size(24.dp),
            tint = ColorPalette.CustomBlue
        )
        Text(
            text = stringResource(R.string.about_screen_title),
            fontFamily = AppTypography.InterBold,
            fontSize = 24.sp,
            color = ColorPalette.CustomBlue,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            AppInfoSection()
        }
        item {
            ApiSection()
        }
        item {
            ContactSection()
        }
    }
}

@Composable
fun AppInfoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.about_screen_app_info_title),
            fontFamily = AppTypography.InterBold,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue
        )
        Text(
            text = stringResource(R.string.about_screen_app_info_version_title),
            fontFamily = AppTypography.InterRegular,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.about_screen_app_info_version_value),
            fontFamily = AppTypography.InterRegular,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.about_screen_app_info_description_title),
            fontFamily = AppTypography.InterRegular,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.about_screen_app_info_description),
            fontFamily = AppTypography.InterRegular,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ApiSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.about_screen_api_title),
            fontFamily = AppTypography.InterBold,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue
        )
        Text(
            text = stringResource(R.string.about_screen_api_datasource_title),
            fontFamily = AppTypography.InterRegular,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue,
            modifier = Modifier.padding(top = 8.dp)
        )
        ApiDescriptionText()
    }
}

@Composable
fun ContactSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.about_screen_contact_title),
            fontFamily = AppTypography.InterBold,
            fontSize = 18.sp,
            color = ColorPalette.CustomBlue
        )
        ClickableContactLink(
            text = stringResource(R.string.about_screen_contact_github_title),
            url = stringResource(R.string.about_screen_contact_github_link),
            icon = {
                Icon(
                    painter = painterResource(R.drawable.github),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp).size(16.dp),
                    tint = Color.DarkGray
                )
            }
        )
        ClickableContactLink(
            text = stringResource(R.string.about_screen_contact_linkedin_title),
            url = stringResource(R.string.about_screen_contact_linkedin_link),
            icon = {
                Icon(
                    painter = painterResource(R.drawable.linkedin),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp).size(16.dp),
                    tint = Color.DarkGray
                )
            }
        )
        ClickableContactLink(
            text = stringResource(R.string.about_screen_contact_email_title),
            url = stringResource(R.string.about_screen_contact_email_link),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp).size(16.dp),
                    tint = Color.DarkGray
                )
            }
        )
    }

}

@Composable
fun ApiDescriptionText() {
    val label = stringResource(id = R.string.about_screen_api_title)
    val url = stringResource(id = R.string.about_screen_api_datasource_link)
    val uriHandler = LocalUriHandler.current
    val template = stringResource(id = R.string.about_screen_api_datasource_description)

    val parts = template.split("%1\$s")

    val annotatedString = buildAnnotatedString {
        append(parts[0])

        pushStringAnnotation(tag = "URL", annotation = url)
        withStyle(
            style = SpanStyle(
                color = Color(0xFF1E88E5)
            )
        ) {
            append(label)
        }
        pop()

        if (parts.size > 1) {
            append(parts[1])
        }
    }

    Text(
        text = annotatedString,
        fontFamily = AppTypography.InterRegular,
        fontSize = 16.sp,
        modifier = Modifier
            .clickable {
            annotatedString.getStringAnnotations(tag = "URL", start = 0, end = annotatedString.length)
                .firstOrNull()?.let {
                    uriHandler.openUri(it.item)
                }
            }.padding(top = 8.dp)
    )
}


@Composable
fun ClickableContactLink(
    text: String,
    url: String,
    tag: String = "URL",
    color: Color = Color.DarkGray,
    icon: @Composable (() -> Unit)? = null
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = tag, annotation = url)
        withStyle(style = SpanStyle(color = color)) {
            append(text)
        }
        pop()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (icon != null) {
            icon()
        }
        Text(
            text = annotatedString,
            fontFamily = AppTypography.InterRegular,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                uriHandler.openUri(url)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        onClickBack = {}
    )
}
