package me.efedaniel.substracker.ui.subscriptions.models

import kotlinx.datetime.Month
import me.efedaniel.substracker.domain.Frequency
import me.efedaniel.substracker.domain.maxAnchorDay

/** Billing frequency option exposed by the add-subscription form. */
enum class FrequencyChoice(
    val label: String,
) {
    Monthly("Monthly"),
    Annually("Annually"),
    OneTime("One-time"),
}

/**
 * Maps a form choice plus the user-entered billing anchors to a domain [Frequency],
 * or null when the anchors are missing/invalid for the choice (domain constructors
 * `require` valid anchors, so this guards against crashes).
 */
internal fun FrequencyChoice.toDomainOrNull(
    billingDay: Int?,
    anchorMonth: Month?,
): Frequency? =
    when (this) {
        FrequencyChoice.Monthly ->
            billingDay
                ?.takeIf { it in 1..31 }
                ?.let { Frequency.Monthly(anchorDay = it) }

        FrequencyChoice.Annually -> {
            if (anchorMonth != null && billingDay != null && billingDay in 1..anchorMonth.maxAnchorDay()) {
                Frequency.Annually(anchorMonth = anchorMonth, anchorDay = billingDay)
            } else {
                null
            }
        }

        FrequencyChoice.OneTime -> Frequency.OneTime
    }
