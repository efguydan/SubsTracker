package me.efedaniel.substracker.ui.subscriptions.models

import kotlinx.datetime.Month
import me.efedaniel.substracker.domain.Frequency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FrequencyChoiceTest {
    @Test
    fun `monthly maps valid billing days`() {
        assertEquals(Frequency.Monthly(anchorDay = 1), FrequencyChoice.Monthly.toDomainOrNull(billingDay = 1, anchorMonth = null))
        assertEquals(Frequency.Monthly(anchorDay = 31), FrequencyChoice.Monthly.toDomainOrNull(billingDay = 31, anchorMonth = null))
    }

    @Test
    fun `monthly rejects missing or out-of-range billing days`() {
        assertNull(FrequencyChoice.Monthly.toDomainOrNull(billingDay = null, anchorMonth = null))
        assertNull(FrequencyChoice.Monthly.toDomainOrNull(billingDay = 0, anchorMonth = null))
        assertNull(FrequencyChoice.Monthly.toDomainOrNull(billingDay = 32, anchorMonth = null))
    }

    @Test
    fun `annually maps valid month and day anchors`() {
        assertEquals(
            Frequency.Annually(anchorMonth = Month.JANUARY, anchorDay = 24),
            FrequencyChoice.Annually.toDomainOrNull(billingDay = 24, anchorMonth = Month.JANUARY),
        )
        // Feb 29 is a valid anchor; common years are handled by the leap-day policy.
        assertEquals(
            Frequency.Annually(anchorMonth = Month.FEBRUARY, anchorDay = 29),
            FrequencyChoice.Annually.toDomainOrNull(billingDay = 29, anchorMonth = Month.FEBRUARY),
        )
    }

    @Test
    fun `annually rejects days beyond the month maximum`() {
        assertNull(FrequencyChoice.Annually.toDomainOrNull(billingDay = 30, anchorMonth = Month.FEBRUARY))
        assertNull(FrequencyChoice.Annually.toDomainOrNull(billingDay = 31, anchorMonth = Month.APRIL))
        assertNull(FrequencyChoice.Annually.toDomainOrNull(billingDay = 0, anchorMonth = Month.JANUARY))
    }

    @Test
    fun `annually rejects missing anchors`() {
        assertNull(FrequencyChoice.Annually.toDomainOrNull(billingDay = null, anchorMonth = Month.JANUARY))
        assertNull(FrequencyChoice.Annually.toDomainOrNull(billingDay = 15, anchorMonth = null))
    }

    @Test
    fun `one-time ignores anchors entirely`() {
        assertEquals(Frequency.OneTime, FrequencyChoice.OneTime.toDomainOrNull(billingDay = null, anchorMonth = null))
        assertEquals(Frequency.OneTime, FrequencyChoice.OneTime.toDomainOrNull(billingDay = 99, anchorMonth = Month.JUNE))
    }
}
