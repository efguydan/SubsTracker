package me.efedaniel.substracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import me.efedaniel.substracker.di.LocalAppGraph
import me.efedaniel.substracker.ui.home.HomeShell
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
@Preview
fun App() {
    // Safe-cast keeps previews (no SubsTrackerApplication) composable with a null graph.
    val appGraph = (LocalContext.current.applicationContext as? SubsTrackerApplication)?.appGraph
    ProtonTheme(darkTheme = false) {
        CompositionLocalProvider(LocalAppGraph provides appGraph) {
            HomeShell(modifier = Modifier)
        }
    }
}
