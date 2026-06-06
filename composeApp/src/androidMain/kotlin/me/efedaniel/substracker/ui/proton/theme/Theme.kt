package me.efedaniel.substracker.ui.proton.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import me.efedaniel.substracker.ui.proton.tokens.color.ProtonColor
import me.efedaniel.substracker.ui.proton.tokens.color.ProtonColorPalette
import me.efedaniel.substracker.ui.proton.tokens.typography.ProtonTypographySystem

private val DarkColorScheme =
    darkColorScheme(
        primary = ProtonColor.Primary,
        onPrimary = ProtonColor.White,
        primaryContainer = ProtonColor.PrimaryContainer,
        onPrimaryContainer = ProtonColor.White,
        secondary = ProtonColor.Secondary,
        onSecondary = ProtonColor.White,
        tertiary = ProtonColor.Tertiary,
        onTertiary = ProtonColor.White,
        background = ProtonColor.OnSurface,
        onBackground = ProtonColor.White,
        surface = ProtonColor.OnSurface,
        onSurface = ProtonColor.White,
        onSurfaceVariant = ProtonColor.OnSurfaceVariantDark,
        surfaceContainerLow = ProtonColor.SurfaceContainerLowDark,
        surfaceContainerLowest = ProtonColor.SurfaceContainerLowestDark,
        surfaceContainerHighest = ProtonColor.SurfaceContainerHighestDark,
        outline = ProtonColor.OutlineDark,
        error = ProtonColor.Error,
        onError = ProtonColor.White,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = ProtonColor.Primary,
        onPrimary = ProtonColor.White,
        primaryContainer = ProtonColor.PrimaryContainer,
        onPrimaryContainer = ProtonColor.White,
        secondary = ProtonColor.Secondary,
        onSecondary = ProtonColor.White,
        tertiary = ProtonColor.Tertiary,
        onTertiary = ProtonColor.White,
        background = ProtonColor.Surface,
        onBackground = ProtonColor.OnSurface,
        surface = ProtonColor.Surface,
        onSurface = ProtonColor.OnSurface,
        onSurfaceVariant = ProtonColor.OnSurfaceVariant,
        surfaceContainerLow = ProtonColor.SurfaceContainerLow,
        surfaceContainerLowest = ProtonColor.SurfaceContainerLowest,
        surfaceContainerHighest = ProtonColor.SurfaceContainerHighest,
        outline = ProtonColor.Outline,
        error = ProtonColor.Error,
        onError = ProtonColor.White,
    )

val LocalProtonColorPalette = staticCompositionLocalOf { ProtonColorPalette.light() }

val LocalProtonTypographySystem = staticCompositionLocalOf { ProtonTypographySystem }

@Composable
fun ProtonTheme(
    darkTheme: Boolean = false,
    lightStatusBarColors: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Fixme: Do we want this on?
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !lightStatusBarColors
        }
    }

    val colorPalette = if (darkTheme) ProtonColorPalette.dark() else ProtonColorPalette.light()
    val typography = ProtonTypographySystem

    CompositionLocalProvider(
        LocalProtonColorPalette provides colorPalette,
        LocalProtonTypographySystem provides typography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
        )
    }
}

object ProtonTheme {
    val colors: ProtonColorPalette
        @Composable
        get() = LocalProtonColorPalette.current

    val typography
        @Composable
        get() = LocalProtonTypographySystem.current
}
