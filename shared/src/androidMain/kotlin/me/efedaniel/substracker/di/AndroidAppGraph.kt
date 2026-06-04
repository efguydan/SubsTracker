package me.efedaniel.substracker.di

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.createGraphFactory
import me.efedaniel.substracker.concurrency.AppDispatchers
import me.efedaniel.substracker.concurrency.DefaultAppDispatchers
import me.efedaniel.substracker.data.db.DatabaseDriverFactory

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
interface AndroidAppGraph : AppGraph {
    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides context: Context,
        ): AndroidAppGraph
    }

    @Provides
    fun provideDriverFactory(context: Context): DatabaseDriverFactory = DatabaseDriverFactory(context)

    @Provides
    @SingleIn(AppScope::class)
    fun provideDispatchers(): AppDispatchers = DefaultAppDispatchers()
}

/**
 * Entry point for the Android app. Pass an application-scoped [Context]
 * (e.g. the `Application` itself); no `applicationContext` unwrapping happens here.
 */
fun createAndroidAppGraph(context: Context): AndroidAppGraph = createGraphFactory<AndroidAppGraph.Factory>().create(context)
