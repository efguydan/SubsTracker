package me.efedaniel.substracker.di

import me.efedaniel.substracker.concurrency.AppDispatchers
import me.efedaniel.substracker.data.SubscriptionRepository

/**
 * Accessors shared across platforms. Deliberately a plain interface — the
 * concrete `@DependencyGraph` lives per platform ([AndroidAppGraph]/[IosAppGraph])
 * because a graph defined in commonMain can't see platform-specific bindings.
 */
interface AppGraph {
    val subscriptionRepository: SubscriptionRepository
    val appDispatchers: AppDispatchers
}
