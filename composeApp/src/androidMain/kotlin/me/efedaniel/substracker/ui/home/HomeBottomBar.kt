package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
fun HomeBottomBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = colors.surfaceContainerLowest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeTabItem(
                icon = Icons.Outlined.Timeline,
                label = "Timeline",
                selected = selectedTab == HomeTab.Timeline,
                onClick = { onTabSelected(HomeTab.Timeline) }
            )
            HomeTabItem(
                icon = Icons.Outlined.Insights,
                label = "Insights",
                selected = selectedTab == HomeTab.Insights,
                onClick = { onTabSelected(HomeTab.Insights) }
            )
            HomeTabItem(
                icon = Icons.Outlined.Subscriptions,
                label = "Subscriptions",
                selected = selectedTab == HomeTab.Subscriptions,
                onClick = { onTabSelected(HomeTab.Subscriptions) }
            )
            HomeTabItem(
                icon = Icons.Outlined.Settings,
                label = "Settings",
                selected = selectedTab == HomeTab.Settings,
                onClick = { onTabSelected(HomeTab.Settings) }
            )
        }
    }
}

@Composable
private fun HomeTabItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = ProtonTheme.colors
    val iconColor = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.6f)
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = iconColor)
        ProtonText(
            text = label,
            color = iconColor,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium
        )
    }
}
