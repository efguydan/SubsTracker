package me.efedaniel.substracker.ui.subscriptions

import me.efedaniel.substracker.domain.Currency

private val PRICE_PATTERN = Regex("""\d*\.?\d*""")

/**
 * Parses user-entered price text into minor units for [currency], or null if invalid.
 *
 * Accepts "," as a decimal separator. Rejects empty input, non-numeric input, and more
 * fractional digits than the currency allows (e.g. any decimals for JPY).
 */
internal fun parsePriceToMinorUnits(
    input: String,
    currency: Currency,
): Long? {
    val normalized = input.trim().replace(',', '.')
    if (normalized.isEmpty() || normalized == ".") return null
    if (!normalized.matches(PRICE_PATTERN)) return null

    val whole = normalized.substringBefore('.').ifEmpty { "0" }
    val fraction = normalized.substringAfter('.', missingDelimiterValue = "")
    if (fraction.length > currency.minorUnitDigits) return null

    return (whole + fraction.padEnd(currency.minorUnitDigits, '0')).toLongOrNull()
}
