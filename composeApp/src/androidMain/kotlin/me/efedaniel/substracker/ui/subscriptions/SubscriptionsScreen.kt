package me.efedaniel.substracker.ui.subscriptions

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.home.HomeScreenScaffold
import me.efedaniel.substracker.ui.home.PlaceholderContent
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
fun SubscriptionsScreen(modifier: Modifier = Modifier) {
    val colors = ProtonTheme.colors
    HomeScreenScaffold(
        title = "Subscriptions",
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colors.secondary,
                contentColor = colors.white,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
            }
        },
    ) { padding ->
        PlaceholderContent(modifier = Modifier.padding(padding))
    }
}
