package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.efedaniel.substracker.ui.insights.InsightsScreen
import me.efedaniel.substracker.ui.navigation.HomeBottomBar
import me.efedaniel.substracker.ui.navigation.HomeTab
import me.efedaniel.substracker.ui.navigation.InsightsRoute
import me.efedaniel.substracker.ui.navigation.SettingsRoute
import me.efedaniel.substracker.ui.navigation.SubscriptionsRoute
import me.efedaniel.substracker.ui.navigation.TimelineRoute
import me.efedaniel.substracker.ui.navigation.route
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.settings.SettingsScreen
import me.efedaniel.substracker.ui.subscriptions.SubscriptionsScreen
import me.efedaniel.substracker.ui.timeline.TimelineScreen

/**
 * Thin root shell: owns only the persistent bottom navigation bar and hosts the [NavHost]. Each
 * destination is a self-contained screen that owns its own `Scaffold` (top bar + optional FAB).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeShell(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val colors = ProtonTheme.colors

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedTab =
        HomeTab.entries.firstOrNull { tab ->
            currentDestination?.hasRoute(tab.route::class) == true
        } ?: HomeTab.Timeline

    Scaffold(
        modifier = modifier,
        containerColor = colors.surface,
        bottomBar = {
            HomeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab -> navController.navigateToTab(tab) },
            )
        },
        // The bottom bar applies its own nav-bar inset; per-screen top bars apply the status-bar
        // inset. The shell injects no insets of its own.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = TimelineRoute,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomPadding)
                    .consumeWindowInsets(PaddingValues(bottom = bottomPadding)),
        ) {
            composable<TimelineRoute> { TimelineScreen() }
            composable<InsightsRoute> { InsightsScreen() }
            composable<SubscriptionsRoute> { SubscriptionsScreen() }
            composable<SettingsRoute> { SettingsScreen() }
        }
    }
}

private fun NavController.navigateToTab(tab: HomeTab) {
    navigate(tab.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
