package com.woowa.nureongi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val nureongiColorScheme = darkColorScheme(
    background = NureongiColors.Background,
    surface = NureongiColors.Surface,
    surfaceVariant = NureongiColors.Surface,
    primary = NureongiColors.Accent,
    onPrimary = NureongiColors.OnAccent,
    onBackground = NureongiColors.TextPrimary,
    onSurface = NureongiColors.TextPrimary,
    onSurfaceVariant = NureongiColors.TextSecondary,
)

@Composable
fun NureongiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = nureongiColorScheme,
        typography = nureongiMaterialTypography,
        content = content,
    )
}
