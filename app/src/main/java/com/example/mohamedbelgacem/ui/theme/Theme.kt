package com.example.mohamedbelgacem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary             = LightBlue,
    onPrimary           = NavyDeep,
    primaryContainer    = PrimaryBlue,
    onPrimaryContainer  = TextPrimary,
    secondary           = GoldAccent,
    onSecondary         = NavyDeep,
    secondaryContainer  = SandTone,
    onSecondaryContainer = NavyDeep,
    tertiary            = SkyBlue,
    onTertiary          = NavyDeep,
    background          = NavyDark,
    onBackground        = TextPrimary,
    surface             = NavyCard,
    onSurface           = TextPrimary,
    surfaceVariant      = NavyMedium,
    onSurfaceVariant    = TextSecondary,
    outline             = TextMuted,
    outlineVariant      = NavyMedium,
    error               = ErrorRed,
    onError             = TextPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary             = PrimaryBlue,
    onPrimary           = Cream,
    primaryContainer    = LightBlue,
    onPrimaryContainer  = NavyDeep,
    secondary           = SandTone,
    onSecondary         = NavyDeep,
    background          = BackgroundLight,
    onBackground        = TextDark,
    surface             = SurfaceLight,
    onSurface           = TextDark,
    surfaceVariant      = Cream,
    onSurfaceVariant    = TextLight,
    error               = ErrorRed,
    onError             = Cream,
)

@Composable
fun TuniQuestTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
