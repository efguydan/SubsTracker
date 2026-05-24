package me.efedaniel.substracker.ui.proton.tokens.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ProtonColorPalette(
    val primary: Color,
    val primaryContainer: Color,
    val secondary: Color,
    val tertiary: Color,
    val surface: Color,
    val surfaceContainerLow: Color,
    val surfaceContainerLowest: Color,
    val onSurface: Color,
    val error: Color,
    val white: Color,
    val transparent: Color,
) {
    companion object {
        fun light() = ProtonColorPalette(
            primary = ProtonColor.Primary,
            primaryContainer = ProtonColor.PrimaryContainer,
            secondary = ProtonColor.Secondary,
            tertiary = ProtonColor.Tertiary,
            surface = ProtonColor.Surface,
            surfaceContainerLow = ProtonColor.SurfaceContainerLow,
            surfaceContainerLowest = ProtonColor.SurfaceContainerLowest,
            onSurface = ProtonColor.OnSurface,
            error = ProtonColor.Error,
            white = ProtonColor.White,
            transparent = ProtonColor.Transparent,
        )

        fun dark() = ProtonColorPalette(
            primary = ProtonColor.Primary,
            primaryContainer = ProtonColor.PrimaryContainer,
            secondary = ProtonColor.Secondary,
            tertiary = ProtonColor.Tertiary,
            surface = ProtonColor.OnSurface,
            surfaceContainerLow = ProtonColor.SurfaceContainerLowDark,
            surfaceContainerLowest = ProtonColor.SurfaceContainerLowestDark,
            onSurface = ProtonColor.White,
            error = ProtonColor.Error,
            white = ProtonColor.White,
            transparent = ProtonColor.Transparent,
        )
    }
}
