package ric.silva.revsample

import org.junit.Assert.assertEquals
import org.junit.Test
import ric.silva.revsample.utils.DecimalUtils

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class TestGeneric {
    @Test
    fun checkDecimalFormat() {
        val centsSeparator = DecimalUtils.getSeparator()
        assertEquals("1"+centsSeparator+"000", DecimalUtils.getDecimalString(1.00, 3))
        assertEquals("1"+centsSeparator+"00", DecimalUtils.getDecimalString(1.00, 2))
        assertEquals("1"+centsSeparator+"0", DecimalUtils.getDecimalString(1.00, 1))
        assertEquals("1", DecimalUtils.getDecimalString(1.00, 0))
    }
}
