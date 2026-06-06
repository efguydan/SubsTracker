package me.efedaniel.substracker.ui.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import me.efedaniel.substracker.data.SubscriptionRepository
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Money
import me.efedaniel.substracker.domain.Subscription
import me.efedaniel.substracker.domain.SubscriptionId
import me.efedaniel.substracker.ui.subscriptions.models.FrequencyChoice
import me.efedaniel.substracker.ui.subscriptions.models.SubscriptionsUiState
import me.efedaniel.substracker.ui.subscriptions.models.toDomainOrNull
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
     * Returns false (without persisting) when the name is blank, the price doesn't parse,
     * or the billing anchors are invalid for the chosen [frequency].
     */
    fun addSubscription(
        name: String,
        priceText: String,
        currency: Currency,
        frequency: FrequencyChoice,
        billingDay: Int?,
        anchorMonth: Month?,
        startDate: LocalDate?,
    ): Boolean {
        val trimmedName = name.trim().ifEmpty { return false }
        val minorUnits = parsePriceToMinorUnits(priceText, currency) ?: return false
        val domainFrequency = frequency.toDomainOrNull(billingDay = billingDay, anchorMonth = anchorMonth) ?: return false

        val now = Clock.System.now()
        viewModelScope.launch {
            repository.add(
                Subscription(
                    id = SubscriptionId(UUID.randomUUID().toString()),
                    name = trimmedName,
                    price = Money(minorUnits = minorUnits, currency = currency),
                    frequency = domainFrequency,
                    // TODO: startDate is not persisted in v1 (no SQLDelight column yet); it lands
                    //  with a future migration — see the SubscriptionMappers header note.
                    startDate = startDate,
                    createdAt = now,
                    updatedAt = now,
                ),
            )
        }
        return true
    }
}
