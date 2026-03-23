package me.efedaniel.substracker.ui.proton.tokens.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ProtonColorPalette(
    val primary: Color = ProtonColor.Primary,
    val primaryContainer: Color = ProtonColor.PrimaryContainer,
    val secondary: Color = ProtonColor.Secondary,
    val tertiary: Color = ProtonColor.Tertiary,
    val surface: Color = ProtonColor.Surface,
    val surfaceContainerLow: Color = ProtonColor.SurfaceContainerLow,
    val surfaceContainerLowest: Color = ProtonColor.SurfaceContainerLowest,
    val onSurface: Color = ProtonColor.OnSurface,
    val error: Color = ProtonColor.Error,
    val white: Color = ProtonColor.White,
    val transparent: Color = ProtonColor.Transparent,
)
