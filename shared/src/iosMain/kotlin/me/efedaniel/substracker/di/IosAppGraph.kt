package me.efedaniel.substracker.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.createGraph
import me.efedaniel.substracker.concurrency.AppDispatchers
import me.efedaniel.substracker.concurrency.DefaultAppDispatchers
import me.efedaniel.substracker.data.db.DatabaseDriverFactory

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
interface IosAppGraph : AppGraph {
    @Provides
    fun provideDriverFactory(): DatabaseDriverFactory = DatabaseDriverFactory()

    @Provides
    @SingleIn(AppScope::class)
    fun provideDispatchers(): AppDispatchers = DefaultAppDispatchers()
}

/** Swift-callable entry point; hold the result for the app's lifetime. */
fun createIosAppGraph(): IosAppGraph = createGraph<IosAppGraph>()
