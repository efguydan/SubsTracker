package me.efedaniel.substracker.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.efedaniel.substracker.data.db.SubsTrackerDatabase
import me.efedaniel.substracker.data.db.SubscriptionEntity
import me.efedaniel.substracker.domain.Subscription
import kotlin.coroutines.CoroutineContext

class SqlDelightSubscriptionRepository(
    database: SubsTrackerDatabase,
    private val queryContext: CoroutineContext,
) : SubscriptionRepository {
    private val queries = database.subscriptionEntityQueries

    override fun observeSubscriptions(): Flow<List<Subscription>> =
        queries
            .selectAll()
            .asFlow()
            .mapToList(queryContext)
            .map { rows -> rows.map(SubscriptionEntity::toDomain) }

    override suspend fun add(subscription: Subscription) {
        withContext(queryContext) {
            queries.insert(
                id = subscription.id.value,
                name = subscription.name,
                price_minor_units = subscription.price.minorUnits,
                price_currency = subscription.price.currency.name,
                frequency_type = subscription.frequency.dbType,
                anchor_day = subscription.frequency.dbAnchorDay,
                anchor_month = subscription.frequency.dbAnchorMonth,
                created_at = subscription.createdAt.toEpochMilliseconds(),
                updated_at = subscription.updatedAt.toEpochMilliseconds(),
            )
        }
    }
}
