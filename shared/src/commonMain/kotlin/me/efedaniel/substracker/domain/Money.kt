package me.efedaniel.substracker.domain

enum class Currency(
    val isoCode: String,
    val minorUnitDigits: Int,
) {
    EUR("EUR", 2),
    USD("USD", 2),
    GBP("GBP", 2),
    JPY("JPY", 0),
}

// Stored in minor units (cents, pence, yen). Never Double — float arithmetic
// loses precision on recurring sums.
data class Money(
    val minorUnits: Long,
    val currency: Currency,
) {
    init {
        require(minorUnits >= 0) { "Money must be non-negative: $minorUnits" }
    }
}
