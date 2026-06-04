package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.efedaniel.substracker.ui.proton.components.topappbar.ProtonTopAppBar
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

/**
 * Per-screen scaffold for the home tabs. Each screen owns its own chrome (top bar + optional FAB)
 * and content; this helper only centralises the [ProtonTopAppBar] + window-inset configuration so
 * the four screens can't drift.
 *
 * Insets: the top status-bar inset is applied by [ProtonTopAppBar]; the bottom nav-bar inset is
 * already reserved/consumed by the [HomeShell], so content insets are zeroed here.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = ProtonTheme.colors.surface,
        topBar = { ProtonTopAppBar(title = title, actions = actions) },
        floatingActionButton = floatingActionButton,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = content,
    )
}
