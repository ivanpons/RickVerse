package com.llimapons.rickverse.designSystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

var DarkColorScheme = darkColorScheme(
    primary = RickVerseGreen,
    background = RickVerseDark,
    surface = RickVerseDark,
    secondary = RickVerseBlue,
    tertiary = RickVerseLight,
    primaryContainer = RickVerseDark,
    onPrimary = RickVerseBlue,
    onBackground = RickVerseLight,
    onSurface = RickVerseLight,
    onSurfaceVariant = RickVerseLight
)

@Composable
fun RickVerseTheme(
    content: @Composable () -> Unit
) {

    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}