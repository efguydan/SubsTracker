package me.efedaniel.substracker.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.efedaniel.substracker.ui.home.HomeScreenScaffold
import me.efedaniel.substracker.ui.home.PlaceholderContent

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    HomeScreenScaffold(title = "Settings", modifier = modifier) { padding ->
        PlaceholderContent(modifier = Modifier.padding(padding))
    }
}
