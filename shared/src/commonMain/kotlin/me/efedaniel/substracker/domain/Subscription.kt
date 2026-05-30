package me.efedaniel.substracker.domain

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline
import kotlin.time.Instant

@JvmInline
value class SubscriptionId(
    val value: String,
)

sealed interface SubscriptionStatus {
    data object Active : SubscriptionStatus

    // Recurring subscription with a natural finite term (e.g. 12-month contract).
    data class FiniteTerm(
        val endsOn: LocalDate,
    ) : SubscriptionStatus

    // User cancelled but the paid period continues until accessUntil. No new charges.
    data class CancelledWithGrace(
        val cancelledAt: Instant,
        val accessUntil: LocalDate,
    ) : SubscriptionStatus

    // Terminal state. Scheduler emits nothing.
    data class Ended(
        val endedOn: LocalDate,
        val reason: EndReason,
    ) : SubscriptionStatus
}

enum class EndReason {
    UserCancelled,
    NaturalTermEnd,
    OneTimeCompleted,
}

data class Subscription(
    val id: SubscriptionId,
    val name: String,
    val price: Money,
    val frequency: Frequency,
    val status: SubscriptionStatus = SubscriptionStatus.Active,
    // Optional billing anchor. Absent -> scheduler uses createdAt.toLocalDate(deviceTz).
    val startDate: LocalDate? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
)
