package me.efedaniel.substracker.ui.subscriptions

import me.efedaniel.substracker.domain.Currency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PriceParsingTest {
    @Test
    fun `parses whole and decimal amounts for two digit currencies`() {
        assertEquals(999, parsePriceToMinorUnits("9.99", Currency.EUR))
        assertEquals(1000, parsePriceToMinorUnits("10", Currency.EUR))
        assertEquals(1050, parsePriceToMinorUnits("10.5", Currency.USD))
        assertEquals(0, parsePriceToMinorUnits("0", Currency.GBP))
        assertEquals(50, parsePriceToMinorUnits(".5", Currency.EUR))
    }

    @Test
    fun `accepts comma as decimal separator`() {
        assertEquals(999, parsePriceToMinorUnits("9,99", Currency.EUR))
    }

    @Test
    fun `trims surrounding whitespace`() {
        assertEquals(999, parsePriceToMinorUnits(" 9.99 ", Currency.EUR))
    }

    @Test
    fun `parses zero decimal currency amounts`() {
        assertEquals(1000, parsePriceToMinorUnits("1000", Currency.JPY))
    }

    @Test
    fun `rejects decimals beyond currency precision`() {
        assertNull(parsePriceToMinorUnits("9.999", Currency.EUR))
        assertNull(parsePriceToMinorUnits("1000.5", Currency.JPY))
    }

    @Test
    fun `rejects invalid input`() {
        assertNull(parsePriceToMinorUnits("", Currency.EUR))
        assertNull(parsePriceToMinorUnits("   ", Currency.EUR))
        assertNull(parsePriceToMinorUnits(".", Currency.EUR))
        assertNull(parsePriceToMinorUnits("abc", Currency.EUR))
        assertNull(parsePriceToMinorUnits("1.2.3", Currency.EUR))
        assertNull(parsePriceToMinorUnits("-5", Currency.EUR))
        assertNull(parsePriceToMinorUnits("9 99", Currency.EUR))
    }
}
