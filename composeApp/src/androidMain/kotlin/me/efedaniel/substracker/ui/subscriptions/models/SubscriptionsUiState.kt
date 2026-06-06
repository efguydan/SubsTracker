package me.efedaniel.substracker.ui.subscriptions.models

import me.efedaniel.substracker.domain.Subscription

data class SubscriptionsUiState(
    val subscriptions: List<Subscription> = emptyList(),
)
