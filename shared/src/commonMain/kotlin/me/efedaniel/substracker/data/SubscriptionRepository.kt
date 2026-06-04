package me.efedaniel.substracker.data

import kotlinx.coroutines.flow.Flow
import me.efedaniel.substracker.domain.Subscription

interface SubscriptionRepository {
    fun observeSubscriptions(): Flow<List<Subscription>>

    suspend fun add(subscription: Subscription)
}
