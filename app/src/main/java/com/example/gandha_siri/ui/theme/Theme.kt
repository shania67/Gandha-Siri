package com.example.gandha_siri.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = SandalBrown,
    onPrimary = PureWhite,

    secondary = LeafGreen,
    onSecondary = PureWhite,

    background = CreamLight,
    onBackground = TextPrimary,

    surface = PureWhite,
    onSurface = TextPrimary,

    error = AlertRed,
    onError = PureWhite
)

@Composable
fun GandhaSiriTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}