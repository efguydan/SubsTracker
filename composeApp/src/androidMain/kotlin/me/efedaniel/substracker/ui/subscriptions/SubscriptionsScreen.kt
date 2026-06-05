package me.efedaniel.substracker.ui.subscriptions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.efedaniel.substracker.di.AppGraph
import me.efedaniel.substracker.di.LocalAppGraph
import me.efedaniel.substracker.ui.home.HomeScreenScaffold
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
fun SubscriptionsScreen(modifier: Modifier = Modifier) {
    when (val appGraph = LocalAppGraph.current) {
        // Previews compose without an Application, so no graph: render static content.
        null -> SubscriptionsScreenContent(subscriptionCount = 0, onAddClick = {}, modifier = modifier)
        else -> SubscriptionsScreenWithData(appGraph = appGraph, modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscriptionsScreenWithData(
    appGraph: AppGraph,
    modifier: Modifier = Modifier,
) {
    val viewModel: SubscriptionsViewModel = viewModel { SubscriptionsViewModel(appGraph.subscriptionRepository) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    SubscriptionsScreenContent(
        subscriptionCount = uiState.subscriptions.size,
        onAddClick = { showAddSheet = true },
        modifier = modifier,
    )

    if (showAddSheet) {
        AddSubscriptionSheet(
            sheetState = sheetState,
            onDismiss = { showAddSheet = false },
            onConfirm = { name, priceText, currency, frequency, billingDay, anchorMonth, startDate ->
                val added =
                    viewModel.addSubscription(
                        name = name,
                        priceText = priceText,
                        currency = currency,
                        frequency = frequency,
                        billingDay = billingDay,
                        anchorMonth = anchorMonth,
                        startDate = startDate,
                    )
                if (added) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showAddSheet = false
                    }
                }
            },
        )
    }
}

@Composable
private fun SubscriptionsScreenContent(
    subscriptionCount: Int,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ProtonTheme.colors
    HomeScreenScaffold(
        title = "Subscriptions",
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = colors.secondary,
                contentColor = colors.white,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
            }
        },
    ) { padding ->
        SubscriptionsCount(count = subscriptionCount, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun SubscriptionsCount(
    count: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ProtonText(
            text =
                when (count) {
                    0 -> "No subscriptions yet"
                    1 -> "You have 1 subscription"
                    else -> "You have $count subscriptions"
                },
            style = ProtonTheme.typography.bodyMedium,
            color = ProtonTheme.colors.onSurface.copy(alpha = 0.6f),
        )
    }
}
