package com.thinh.pomodoro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

private val WorkingLightColors = lightColorScheme(
    primary = working_light_primary,
    secondary = working_light_secondary,
    tertiary = working_light_tertiary,
    background = working_light_background,
)

private val WorkingDarkColors = darkColorScheme(
    primary = working_dark_primary,
    secondary = working_dark_secondary,
    tertiary = working_dark_tertiary,
    background = working_dark_background,
)

private val ShortBreakLightColors = lightColorScheme(
    primary = short_break_light_primary,
    secondary = short_break_light_secondary,
    tertiary = short_break_light_tertiary,
    background = short_break_light_background,
)

private val ShortBreakDarkColors = darkColorScheme(
    primary = short_break_dark_primary,
    secondary = short_break_dark_secondary,
    tertiary = short_break_dark_tertiary,
    background = short_break_dark_background,
)

private val LongBreakLightColors = lightColorScheme(
    primary = long_break_light_primary,
    secondary = long_break_light_secondary,
    tertiary = long_break_light_tertiary,
    background = long_break_light_background,
)

private val LongBreakDarkColors = darkColorScheme(
    primary = long_break_dark_primary,
    secondary = long_break_dark_secondary,
    tertiary = long_break_dark_tertiary,
    background = long_break_dark_background,
)

@Composable
fun PomodoroTheme(
    darkMode: Boolean,
    pomodoroColorScheme: PomodoroColorScheme,
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkMode) {
        when (pomodoroColorScheme) {
            PomodoroColorScheme.WORKING_COLOR -> WorkingLightColors
            PomodoroColorScheme.SHORT_BREAK_COLOR -> ShortBreakLightColors
            PomodoroColorScheme.LONG_BREAK_COLOR -> LongBreakLightColors
        }
    } else {
        when (pomodoroColorScheme) {
            PomodoroColorScheme.WORKING_COLOR -> WorkingDarkColors
            PomodoroColorScheme.SHORT_BREAK_COLOR -> ShortBreakDarkColors
            PomodoroColorScheme.LONG_BREAK_COLOR -> LongBreakDarkColors
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

enum class PomodoroColorScheme {
    WORKING_COLOR,
    SHORT_BREAK_COLOR,
    LONG_BREAK_COLOR
}