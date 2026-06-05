package me.efedaniel.substracker.domain

import kotlinx.datetime.Month

sealed interface Frequency {
    data class Monthly(
        val anchorDay: Int,
        val rollover: RolloverPolicy = RolloverPolicy.LastDayOfMonth,
    ) : Frequency {
        init {
            require(anchorDay in 1..31) { "anchorDay out of range: $anchorDay" }
        }
    }

    data class Annually(
        val anchorMonth: Month,
        val anchorDay: Int,
        val leapDayPolicy: LeapDayPolicy = LeapDayPolicy.Feb28OnCommonYears,
    ) : Frequency {
        init {
            val maxDay = anchorMonth.maxAnchorDay()
            require(anchorDay in 1..maxDay) {
                "anchorDay $anchorDay invalid for $anchorMonth (max $maxDay)"
            }
        }
    }

    data object OneTime : Frequency
}

/** Largest anchor day selectable for this month (Feb 29 is valid; leap handling is policy-driven). */
fun Month.maxAnchorDay(): Int =
    when (this) {
        Month.FEBRUARY -> 29
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }

enum class RolloverPolicy {
    // anchorDay=31 in Feb -> Feb 28/29.
    LastDayOfMonth,

    // anchorDay=31 in Feb -> Mar 3 (or Mar 2 in leap years).
    OverflowToNextMonth,
}

enum class LeapDayPolicy {
    // Feb 29 anchor -> Feb 28 in common years.
    Feb28OnCommonYears,

    // Feb 29 anchor -> Mar 1 in common years.
    Mar1OnCommonYears,
}
