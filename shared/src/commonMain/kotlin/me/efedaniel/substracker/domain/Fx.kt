package me.efedaniel.substracker.domain

import kotlin.time.Instant

data class FxRate(
    val from: Currency,
    val to: Currency,
    val rate: Double,
    val asOf: Instant,
)

data class MonetaryAmount(
    val native: Money,
    val converted: Money,
    val rate: FxRate,
    val freshness: FxFreshness,
)

enum class FxFreshness {
    Fresh,
    Stale,
}

// Cross-conversion goes through USD: A -> USD -> B using two cached rates.
// Implementations are pure lookups — refreshing is handled by an outer layer.
interface FxRateProvider {
    fun rate(
        from: Currency,
        to: Currency,
        at: Instant,
    ): FxRate?
}
