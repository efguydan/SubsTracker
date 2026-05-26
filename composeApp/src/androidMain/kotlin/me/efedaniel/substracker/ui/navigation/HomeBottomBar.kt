package me.efedaniel.substracker.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension

private val BottomBarHeight = ProtonDimension.ComponentSize64

@Composable
fun HomeBottomBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ProtonTheme.colors
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.surfaceContainerLowest,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(BottomBarHeight),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            HomeTab.entries.forEach { tab ->
                HomeTabItem(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    icon = tab.icon,
                    label = tab.label,
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                )
            }
        }
    }
}

@Composable
private fun HomeTabItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ProtonTheme.colors
    val tint = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.6f)
    Column(
        modifier =
            modifier
                .selectable(
                    selected = selected,
                    role = Role.Tab,
                    onClick = onClick,
                )
                .heightIn(min = ProtonDimension.ComponentSize48)
                .padding(horizontal = ProtonDimension.Spacing8, vertical = ProtonDimension.Spacing4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
        Spacer(modifier = Modifier.height(ProtonDimension.Spacing2))
        ProtonText(
            text = label,
            color = tint,
            style = ProtonTheme.typography.labelMedium,
        )
    }
}

private val HomeTab.icon: ImageVector
    get() =
        when (this) {
            HomeTab.Timeline -> Icons.Outlined.Timeline
            HomeTab.Insights -> Icons.Outlined.Insights
            HomeTab.Subscriptions -> Icons.Outlined.Subscriptions
            HomeTab.Settings -> Icons.Outlined.Settings
        }

private val HomeTab.label: String
    get() =
        when (this) {
            HomeTab.Timeline -> "Timeline"
            HomeTab.Insights -> "Insights"
            HomeTab.Subscriptions -> "Subscriptions"
            HomeTab.Settings -> "Settings"
        }
