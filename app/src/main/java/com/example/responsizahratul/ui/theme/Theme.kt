package com.example.responsizahratul.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Skema warna untuk Mode Gelap (Dark Mode) bertema gaming
private val DarkColorScheme = darkColorScheme(
    primary = GamePrimaryDark,
    onPrimary = GameOnPrimaryDark,
    primaryContainer = GamePrimaryContainerDark,
    onPrimaryContainer = GameOnPrimaryContainerDark,
    secondary = GameSecondaryDark,
    onSecondary = GameOnSecondaryDark,
    secondaryContainer = GameSecondaryContainerDark,
    onSecondaryContainer = GameOnSecondaryContainerDark,
    background = GameBackgroundDark,
    onBackground = GameOnBackgroundDark,
    surface = GameSurfaceDark,
    onSurface = GameOnSurfaceDark,
    surfaceVariant = GameSurfaceVariantDark,
    onSurfaceVariant = GameOnSurfaceVariantDark
)

// Skema warna untuk Mode Terang (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary = GamePrimaryLight,
    onPrimary = GameOnPrimaryLight,
    primaryContainer = GamePrimaryContainerLight,
    onPrimaryContainer = GameOnPrimaryContainerLight,
    secondary = GameSecondaryLight,
    onSecondary = GameOnSecondaryLight,
    secondaryContainer = GameSecondaryContainerLight,
    onSecondaryContainer = GameOnSecondaryContainerLight,
    background = GameBackgroundLight,
    onBackground = GameOnBackgroundLight,
    surface = GameSurfaceLight,
    onSurface = GameOnSurfaceLight,
    surfaceVariant = GameSurfaceVariantLight,
    onSurfaceVariant = GameOnSurfaceVariantLight
)

@Composable
fun ResponsizahratulTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}