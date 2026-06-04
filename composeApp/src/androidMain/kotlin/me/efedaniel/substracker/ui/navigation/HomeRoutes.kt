package me.efedaniel.substracker.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations for the home bottom-nav graph.
 *
 * Convention: the route is the `@Serializable` object named `XRoute`; the matching composable is
 * `XScreen`. Each screen owns its own `Scaffold` (see `HomeScreenScaffold`).
 */
sealed interface HomeRoute

@Serializable
data object TimelineRoute : HomeRoute

@Serializable
data object InsightsRoute : HomeRoute

@Serializable
data object SubscriptionsRoute : HomeRoute

@Serializable
data object SettingsRoute : HomeRoute

val HomeTab.route: HomeRoute
    get() =
        when (this) {
            HomeTab.Timeline -> TimelineRoute
            HomeTab.Insights -> InsightsRoute
            HomeTab.Subscriptions -> SubscriptionsRoute
            HomeTab.Settings -> SettingsRoute
        }
