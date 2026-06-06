package me.efedaniel.substracker.ui.subscriptions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import me.efedaniel.substracker.data.SubscriptionRepository
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Frequency
import me.efedaniel.substracker.domain.Subscription
import me.efedaniel.substracker.ui.subscriptions.models.FrequencyChoice
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SubscriptionsViewModelTest {
    private val repository = FakeSubscriptionRepository()
    private lateinit var viewModel: SubscriptionsViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = SubscriptionsViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `rejects blank name`() =
        runTest {
            assertFalse(addValidMonthly(name = "   "))
            assertTrue(repository.added.isEmpty())
        }

    @Test
    fun `rejects unparseable price`() =
        runTest {
            assertFalse(addValidMonthly(priceText = "abc"))
            assertFalse(addValidMonthly(priceText = "9.999"))
            assertTrue(repository.added.isEmpty())
        }

    @Test
    fun `rejects invalid billing day for monthly`() =
        runTest {
            assertFalse(addValidMonthly(billingDay = null))
            assertFalse(addValidMonthly(billingDay = 0))
            assertFalse(addValidMonthly(billingDay = 32))
            assertTrue(repository.added.isEmpty())
        }

    @Test
    fun `rejects invalid annual anchors`() =
        runTest {
            val added =
                viewModel.addSubscription(
                    name = "Domain",
                    priceText = "12.00",
                    currency = Currency.EUR,
                    frequency = FrequencyChoice.Annually,
                    billingDay = 30,
                    anchorMonth = Month.FEBRUARY,
                    startDate = null,
                )
            assertFalse(added)
            assertTrue(repository.added.isEmpty())
        }

    @Test
    fun `adds monthly subscription with trimmed name and parsed price`() =
        runTest {
            assertTrue(addValidMonthly(name = "  Netflix  ", priceText = "9.99", billingDay = 24))

            val subscription = repository.added.single()
            assertEquals("Netflix", subscription.name)
            assertEquals(999, subscription.price.minorUnits)
            assertEquals(Currency.EUR, subscription.price.currency)
            assertEquals(Frequency.Monthly(anchorDay = 24), subscription.frequency)
            assertNull(subscription.startDate)
        }

    @Test
    fun `adds annual subscription with start date on the domain object`() =
        runTest {
            val startDate = LocalDate(2026, 1, 24)
            val added =
                viewModel.addSubscription(
                    name = "Prime",
                    priceText = "95",
                    currency = Currency.USD,
                    frequency = FrequencyChoice.Annually,
                    billingDay = 24,
                    anchorMonth = Month.JANUARY,
                    startDate = startDate,
                )
            assertTrue(added)

            val subscription = repository.added.single()
            assertEquals(Frequency.Annually(anchorMonth = Month.JANUARY, anchorDay = 24), subscription.frequency)
            // Note: startDate currently survives only on the domain object — not persisted in v1.
            assertEquals(startDate, subscription.startDate)
        }

    @Test
    fun `adds one-time subscription without anchors`() =
        runTest {
            val added =
                viewModel.addSubscription(
                    name = "Lifetime license",
                    priceText = "1000",
                    currency = Currency.JPY,
                    frequency = FrequencyChoice.OneTime,
                    billingDay = null,
                    anchorMonth = null,
                    startDate = null,
                )
            assertTrue(added)
            assertEquals(Frequency.OneTime, repository.added.single().frequency)
        }

    private fun addValidMonthly(
        name: String = "Netflix",
        priceText: String = "9.99",
        billingDay: Int? = 24,
    ): Boolean =
        viewModel.addSubscription(
            name = name,
            priceText = priceText,
            currency = Currency.EUR,
            frequency = FrequencyChoice.Monthly,
            billingDay = billingDay,
            anchorMonth = null,
            startDate = null,
        )
}

private class FakeSubscriptionRepository : SubscriptionRepository {
    val added = mutableListOf<Subscription>()
    private val subscriptions = MutableStateFlow<List<Subscription>>(emptyList())

    override fun observeSubscriptions(): Flow<List<Subscription>> = subscriptions

    override suspend fun add(subscription: Subscription) {
        added += subscription
        subscriptions.value = subscriptions.value + subscription
    }
}
