package com.example.mohamedbelgacem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Light color scheme for Tunisia Heritage Quest
 * Features Mediterranean blue and sand tones
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Cream,
    primaryContainer = LightBlue,
    onPrimaryContainer = DarkBlue,
    secondary = SandTone,
    onSecondary = DarkBlue,
    secondaryContainer = Cream,
    onSecondaryContainer = DarkBlue,
    tertiary = LightBlue,
    onTertiary = Cream,
    tertiaryContainer = LightBlue,
    onTertiaryContainer = DarkBlue,
    error = ErrorRed,
    onError = Cream,
    errorContainer = ErrorRed,
    onErrorContainer = Cream,
    background = BackgroundLight,
    onBackground = TextDark,
    surface = SurfaceLight,
    onSurface = TextDark,
    surfaceVariant = Cream,
    onSurfaceVariant = TextLight,
    outline = TextLight,
    outlineVariant = SandTone
)

/**
 * Dark color scheme for Tunisia Heritage Quest
 * Maintains visual identity with darker backgrounds
 */
private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    onPrimary = DarkBlue,
    primaryContainer = PrimaryBlue,
    onPrimaryContainer = Cream,
    secondary = SandTone,
    onSecondary = DarkBlue,
    secondaryContainer = PrimaryBlue,
    onSecondaryContainer = SandTone,
    tertiary = LightBlue,
    onTertiary = DarkBlue,
    tertiaryContainer = PrimaryBlue,
    onTertiaryContainer = Cream,
    error = ErrorRed,
    onError = DarkBlue,
    errorContainer = ErrorRed,
    onErrorContainer = Cream,
    background = DarkBlue,
    onBackground = Cream,
    surface = PrimaryBlue,
    onSurface = Cream,
    surfaceVariant = PrimaryBlue,
    onSurfaceVariant = SandTone,
    outline = SandTone,
    outlineVariant = PrimaryBlue
)

/**
 * Tunisia Heritage Quest Theme
 *
 * Applies Material Design 3 theming with custom color palette
 * and typography tailored for the quiz application.
 *
 * @param darkTheme Whether to apply dark color scheme (default: false)
 * @param content The composable content to apply theme to
 */
@Composable
fun TunisiaHeritageQuestTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}