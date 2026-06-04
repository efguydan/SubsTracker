package me.efedaniel.substracker.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Month
import me.efedaniel.substracker.data.db.SubsTrackerDatabase
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Frequency
import me.efedaniel.substracker.domain.Money
import me.efedaniel.substracker.domain.Subscription
import me.efedaniel.substracker.domain.SubscriptionId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class SqlDelightSubscriptionRepositoryTest {
    private fun TestScope.createRepository(): SqlDelightSubscriptionRepository =
        SqlDelightSubscriptionRepository(
            database = SubsTrackerDatabase(createInMemorySqlDriver()),
            queryContext = UnconfinedTestDispatcher(testScheduler),
        )

    @Test
    fun observeSubscriptionsIsEmptyByDefault() =
        runTest {
            val repository = createRepository()

            assertEquals(emptyList(), repository.observeSubscriptions().first())
        }

    @Test
    fun monthlySubscriptionRoundTrips() =
        runTest {
            val repository = createRepository()
            val subscription =
                subscription(
                    frequency = Frequency.Monthly(anchorDay = 15),
                    price = Money(minorUnits = 1599, currency = Currency.EUR),
                )

            repository.add(subscription)

            assertEquals(listOf(subscription), repository.observeSubscriptions().first())
        }

    @Test
    fun annualSubscriptionRoundTrips() =
        runTest {
            val repository = createRepository()
            val subscription =
                subscription(
                    frequency = Frequency.Annually(anchorMonth = Month.FEBRUARY, anchorDay = 29),
                    price = Money(minorUnits = 9900, currency = Currency.JPY),
                )

            repository.add(subscription)

            assertEquals(listOf(subscription), repository.observeSubscriptions().first())
        }

    @Test
    fun oneTimeSubscriptionRoundTrips() =
        runTest {
            val repository = createRepository()
            val subscription =
                subscription(
                    frequency = Frequency.OneTime,
                    price = Money(minorUnits = 4999, currency = Currency.GBP),
                )

            repository.add(subscription)

            assertEquals(listOf(subscription), repository.observeSubscriptions().first())
        }

    @Test
    fun subscriptionsAreOrderedByCreatedAtDescending() =
        runTest {
            val repository = createRepository()
            repository.add(subscription(id = "oldest", createdAt = Instant.fromEpochMilliseconds(1_000)))
            repository.add(subscription(id = "newest", createdAt = Instant.fromEpochMilliseconds(3_000)))
            repository.add(subscription(id = "middle", createdAt = Instant.fromEpochMilliseconds(2_000)))

            val ids = repository.observeSubscriptions().first().map { it.id.value }

            assertEquals(listOf("newest", "middle", "oldest"), ids)
        }

    @Test
    fun observeSubscriptionsEmitsUpdatedListAfterAdd() =
        runTest {
            val repository = createRepository()
            val first = subscription(id = "first", createdAt = Instant.fromEpochMilliseconds(1_000))
            val second = subscription(id = "second", createdAt = Instant.fromEpochMilliseconds(2_000))
            val emissions = mutableListOf<List<Subscription>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                repository.observeSubscriptions().toList(emissions)
            }

            repository.add(first)
            repository.add(second)

            assertTrue(emissions.isNotEmpty(), "expected at least one emission")
            assertEquals(listOf(second, first), emissions.last())
        }

    private fun subscription(
        id: String = "sub-1",
        name: String = "Netflix",
        price: Money = Money(minorUnits = 1599, currency = Currency.USD),
        frequency: Frequency = Frequency.Monthly(anchorDay = 15),
        createdAt: Instant = Instant.fromEpochMilliseconds(1_000),
    ): Subscription =
        Subscription(
            id = SubscriptionId(id),
            name = name,
            price = price,
            frequency = frequency,
            createdAt = createdAt,
            updatedAt = createdAt,
        )
}
