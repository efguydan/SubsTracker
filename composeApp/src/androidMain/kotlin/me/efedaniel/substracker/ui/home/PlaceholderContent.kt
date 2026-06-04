package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

/** Shared "Coming soon" body for home tabs that don't have real content yet. */
@Composable
fun PlaceholderContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ProtonText(
            text = "Coming soon",
            style = ProtonTheme.typography.bodyMedium,
            color = ProtonTheme.colors.onSurface.copy(alpha = 0.6f),
        )
    }
}
