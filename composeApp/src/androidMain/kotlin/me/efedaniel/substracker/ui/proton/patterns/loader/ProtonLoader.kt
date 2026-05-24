package me.efedaniel.substracker.ui.proton.patterns.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.utility.extensions.conditional

@Composable
fun ProtonLoader(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = true,
) {
    Box(
        modifier =
            Modifier
                .conditional(isFullScreen) { fillMaxSize() },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = ProtonTheme.colors.white)
    }
}
