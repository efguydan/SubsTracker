package me.efedaniel.substracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.navigation.HomeBottomBar
import me.efedaniel.substracker.ui.navigation.HomeTab
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.components.topappbar.ProtonTopAppBar
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.timeline.TimelineRoute

@Composable
@Preview
fun App() {
    ProtonTheme(darkTheme = false) {
        RootScaffold(modifier = Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RootScaffold(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(HomeTab.Timeline) }
    val colors = ProtonTheme.colors
    Scaffold(
        modifier = modifier,
        containerColor = colors.surface,
        topBar = {
            ProtonTopAppBar(title = selectedTab.toolbarTitle)
        },
        bottomBar = {
            HomeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
        floatingActionButton = {
            if (selectedTab == HomeTab.Timeline) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = colors.secondary,
                    contentColor = colors.white,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
                }
            }
        },
    ) { padding ->
        when (selectedTab) {
            HomeTab.Timeline -> TimelineRoute(modifier = Modifier.padding(padding))
            HomeTab.Insights,
            HomeTab.Subscriptions,
            HomeTab.Settings,
            -> PlaceholderScreen(modifier = Modifier.padding(padding))
        }
    }
}

@Composable
private fun PlaceholderScreen(modifier: Modifier = Modifier) {
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

private val HomeTab.toolbarTitle: String
    get() =
        when (this) {
            HomeTab.Timeline -> "Lighter"
            HomeTab.Insights -> "Insights"
            HomeTab.Subscriptions -> "Subscriptions"
            HomeTab.Settings -> "Settings"
        }
