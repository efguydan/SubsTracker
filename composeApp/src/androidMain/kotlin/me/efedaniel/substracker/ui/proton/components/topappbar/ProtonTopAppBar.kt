package me.efedaniel.substracker.ui.proton.components.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtonTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = ProtonTopAppBarDefaults.colors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            ProtonText(
                text = title,
                style = ProtonTheme.typography.headlineMedium,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

object ProtonTopAppBarDefaults {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun colors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = ProtonTheme.colors.surface,
        titleContentColor = ProtonTheme.colors.onSurface,
        navigationIconContentColor = ProtonTheme.colors.onSurface,
        actionIconContentColor = ProtonTheme.colors.onSurface,
    )
}
