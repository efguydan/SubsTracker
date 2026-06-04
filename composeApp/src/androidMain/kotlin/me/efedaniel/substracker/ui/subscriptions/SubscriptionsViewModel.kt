package me.efedaniel.substracker.ui.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.efedaniel.substracker.data.SubscriptionRepository
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Money
import me.efedaniel.substracker.domain.Subscription
import me.efedaniel.substracker.domain.SubscriptionId
import java.util.UUID
import kotlin.time.Clock

class SubscriptionsViewModel(
    private val repository: SubscriptionRepository,
) : ViewModel() {
    val uiState: StateFlow<SubscriptionsUiState> =
        repository.observeSubscriptions()
            .map { SubscriptionsUiState(subscriptions = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = SubscriptionsUiState(),
            )

    /**
     * Validates the raw form values and persists a new subscription.
     * Returns false (without persisting) when the name is blank or the price doesn't parse.
     */
    fun addSubscription(
        name: String,
        priceText: String,
        currency: Currency,
        frequency: FrequencyChoice,
    ): Boolean {
        val trimmedName = name.trim().ifEmpty { return false }
        val minorUnits = parsePriceToMinorUnits(priceText, currency) ?: return false

        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        viewModelScope.launch {
            repository.add(
                Subscription(
                    id = SubscriptionId(UUID.randomUUID().toString()),
                    name = trimmedName,
                    price = Money(minorUnits = minorUnits, currency = currency),
                    frequency = frequency.toDomain(today),
                    createdAt = now,
                    updatedAt = now,
                ),
            )
        }
        return true
    }
}

data class SubscriptionsUiState(
    val subscriptions: List<Subscription> = emptyList(),
)
