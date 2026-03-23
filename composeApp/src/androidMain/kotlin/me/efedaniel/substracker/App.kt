package me.efedaniel.substracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.home.HomeBottomBar
import me.efedaniel.substracker.ui.home.HomeRoute
import me.efedaniel.substracker.ui.home.HomeTab
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
@Preview
fun App() {
    ProtonTheme(darkTheme = false) {
        RootScaffold(modifier = Modifier)
    }
}

@Composable
private fun RootScaffold(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(HomeTab.Timeline) }
    val colors = ProtonTheme.colors
    Scaffold(
        modifier = modifier,
        containerColor = colors.surface,
        bottomBar = {
            HomeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            if (selectedTab == HomeTab.Timeline) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = colors.secondary,
                    contentColor = colors.white,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            HomeTab.Timeline -> HomeRoute(modifier = Modifier.padding(padding))
            HomeTab.Insights -> PlaceholderScreen(title = "Insights", modifier = Modifier.padding(padding))
            HomeTab.Subscriptions -> PlaceholderScreen(title = "Subscriptions", modifier = Modifier.padding(padding))
            HomeTab.Settings -> PlaceholderScreen(title = "Settings", modifier = Modifier.padding(padding))
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = title,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            color = ProtonTheme.colors.onSurface
        )
    }
}
