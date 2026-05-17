package com.example.litert.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = TealSoft,
    onPrimary = Ink,
    secondary = CoralSoft,
    onSecondary = Ink,
    tertiary = SurfaceTint,
    onTertiary = Ink,
    background = Color(0xFF171410),
    onBackground = Color(0xFFF2EBDD),
    surface = Color(0xFF201C17),
    onSurface = Color(0xFFF2EBDD),
    surfaceVariant = Color(0xFF2A241E),
    onSurfaceVariant = Color(0xFFD7CAB7),
    outline = Color(0xFF5D544A),
    outlineVariant = Color(0xFF3C352D),
    error = Color(0xFFE58A7B),
    onError = Ink
)

private val LightColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    secondary = Coral,
    onSecondary = Color.White,
    tertiary = Slate,
    onTertiary = Color.White,
    background = SurfacePaper,
    onBackground = Ink,
    surface = SurfacePaperAlt,
    onSurface = Ink,
    surfaceVariant = SurfaceTint,
    onSurfaceVariant = Slate,
    outline = Line,
    outlineVariant = Color(0xFFE8DDCB),
    error = Color(0xFFC55248),
    onError = Color.White
)

@Composable
fun LiteRtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
