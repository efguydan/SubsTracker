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
    val surfaceContainerHighest: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val error: Color,
    val white: Color,
    val transparent: Color,
) {
    companion object {
        fun light() =
            ProtonColorPalette(
                primary = ProtonColor.Primary,
                primaryContainer = ProtonColor.PrimaryContainer,
                secondary = ProtonColor.Secondary,
                tertiary = ProtonColor.Tertiary,
                surface = ProtonColor.Surface,
                surfaceContainerLow = ProtonColor.SurfaceContainerLow,
                surfaceContainerLowest = ProtonColor.SurfaceContainerLowest,
                surfaceContainerHighest = ProtonColor.SurfaceContainerHighest,
                onSurface = ProtonColor.OnSurface,
                onSurfaceVariant = ProtonColor.OnSurfaceVariant,
                outline = ProtonColor.Outline,
                error = ProtonColor.Error,
                white = ProtonColor.White,
                transparent = ProtonColor.Transparent,
            )

        fun dark() =
            ProtonColorPalette(
                primary = ProtonColor.Primary,
                primaryContainer = ProtonColor.PrimaryContainer,
                secondary = ProtonColor.Secondary,
                tertiary = ProtonColor.Tertiary,
                surface = ProtonColor.OnSurface,
                surfaceContainerLow = ProtonColor.SurfaceContainerLowDark,
                surfaceContainerLowest = ProtonColor.SurfaceContainerLowestDark,
                surfaceContainerHighest = ProtonColor.SurfaceContainerHighestDark,
                onSurface = ProtonColor.White,
                onSurfaceVariant = ProtonColor.OnSurfaceVariantDark,
                outline = ProtonColor.OutlineDark,
                error = ProtonColor.Error,
                white = ProtonColor.White,
                transparent = ProtonColor.Transparent,
            )
    }
}
