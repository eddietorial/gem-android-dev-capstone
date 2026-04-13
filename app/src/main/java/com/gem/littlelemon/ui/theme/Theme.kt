package com.gem.littlelemon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LittleLemonColorScheme = lightColorScheme(
    primary          = Primary1,
    onPrimary        = Highlight1,
    secondary        = Primary2,
    onSecondary      = Highlight2,
    tertiary         = Secondary1,
    background       = Highlight1,
    onBackground     = Highlight2,
    surface          = Highlight1,
    onSurface        = Highlight2
)

@Composable
fun LittleLemonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LittleLemonColorScheme,
        typography  = Typography,
        content     = content
    )
}
