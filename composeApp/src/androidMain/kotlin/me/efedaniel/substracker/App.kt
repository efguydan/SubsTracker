package me.efedaniel.substracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.efedaniel.substracker.ui.home.HomeShell
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
@Preview
fun App() {
    ProtonTheme(darkTheme = false) {
        HomeShell(modifier = Modifier)
    }
}
