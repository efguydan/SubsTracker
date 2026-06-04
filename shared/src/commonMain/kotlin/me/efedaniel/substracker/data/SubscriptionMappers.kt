package me.efedaniel.substracker.data

import kotlinx.datetime.Month
import kotlinx.datetime.number
import me.efedaniel.substracker.data.db.SubscriptionEntity
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Frequency
import me.efedaniel.substracker.domain.Money
import me.efedaniel.substracker.domain.Subscription
import me.efedaniel.substracker.domain.SubscriptionId
import kotlin.time.Instant

/*
 * v1 limitation: status, rollover/leap-day policies, and startDate are not
 * persisted. Reads assume SubscriptionStatus.Active, default policies, and a
 * null startDate. They become columns via migrations when edit/cancel land.
 */

internal object FrequencyType {
    const val MONTHLY = "MONTHLY"
    const val ANNUALLY = "ANNUALLY"
    const val ONE_TIME = "ONE_TIME"
}

internal fun SubscriptionEntity.toDomain(): Subscription =
    Subscription(
        id = SubscriptionId(id),
        name = name,
        price = Money(minorUnits = price_minor_units, currency = Currency.valueOf(price_currency)),
        frequency = toFrequency(),
        createdAt = Instant.fromEpochMilliseconds(created_at),
        updatedAt = Instant.fromEpochMilliseconds(updated_at),
    )

private fun SubscriptionEntity.toFrequency(): Frequency =
    when (frequency_type) {
        FrequencyType.MONTHLY ->
            Frequency.Monthly(
                anchorDay = requireNotNull(anchor_day) { "anchor_day missing for $id" }.toInt(),
            )
        FrequencyType.ANNUALLY ->
            Frequency.Annually(
                anchorMonth = Month(requireNotNull(anchor_month) { "anchor_month missing for $id" }.toInt()),
                anchorDay = requireNotNull(anchor_day) { "anchor_day missing for $id" }.toInt(),
            )
        FrequencyType.ONE_TIME -> Frequency.OneTime
        else -> error("Unknown frequency_type '$frequency_type' for $id")
    }

internal val Frequency.dbType: String
    get() =
        when (this) {
            is Frequency.Monthly -> FrequencyType.MONTHLY
            is Frequency.Annually -> FrequencyType.ANNUALLY
            Frequency.OneTime -> FrequencyType.ONE_TIME
        }

internal val Frequency.dbAnchorDay: Long?
    get() =
        when (this) {
            is Frequency.Monthly -> anchorDay.toLong()
            is Frequency.Annually -> anchorDay.toLong()
            Frequency.OneTime -> null
        }

internal val Frequency.dbAnchorMonth: Long?
    get() =
        when (this) {
            is Frequency.Annually -> anchorMonth.number.toLong()
            is Frequency.Monthly, Frequency.OneTime -> null
        }
