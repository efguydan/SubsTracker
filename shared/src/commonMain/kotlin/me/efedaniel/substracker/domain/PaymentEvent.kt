package me.efedaniel.substracker.domain

import kotlinx.datetime.LocalDate

data class PaymentEvent(
    val subscriptionId: SubscriptionId,
    // Snapshot of the subscription name at event-emission time. Renames don't mutate past events.
    val subscriptionName: String,
    val dueOn: LocalDate,
    val nativeAmount: Money,
    val convertedAmount: MonetaryAmount?,
    val kind: Kind,
) {
    enum class Kind {
        Recurring,
        FinalBeforeTermEnd,
        GraceFinalAccess,
        OneTime,
    }
}
