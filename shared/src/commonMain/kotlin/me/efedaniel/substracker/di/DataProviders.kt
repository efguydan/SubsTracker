package me.efedaniel.substracker.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import me.efedaniel.substracker.concurrency.AppDispatchers
import me.efedaniel.substracker.data.SqlDelightSubscriptionRepository
import me.efedaniel.substracker.data.SubscriptionRepository
import me.efedaniel.substracker.data.db.DatabaseDriverFactory
import me.efedaniel.substracker.data.db.SubsTrackerDatabase
import me.efedaniel.substracker.data.db.createDatabase

/**
 * Bindings for the data layer. Data/domain classes stay free of DI
 * annotations; all wiring is centralized in provider interfaces like this one.
 */
@ContributesTo(AppScope::class)
interface DataProviders {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(driverFactory: DatabaseDriverFactory): SubsTrackerDatabase = createDatabase(driverFactory)

    @Provides
    @SingleIn(AppScope::class)
    fun provideSubscriptionRepository(
        database: SubsTrackerDatabase,
        dispatchers: AppDispatchers,
    ): SubscriptionRepository =
        SqlDelightSubscriptionRepository(
            database = database,
            queryContext = dispatchers.io,
        )
}
