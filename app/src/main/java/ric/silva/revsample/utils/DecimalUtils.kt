package ric.silva.revsample.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object DecimalUtils {

    private var nf: NumberFormat = NumberFormat.getInstance(Locale.getDefault())
    private var decimalFormat: DecimalFormat
    private var centsSeparator: Char

    init {
        decimalFormat = nf as DecimalFormat
        centsSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
    }

    fun getSeparator() : Char{
        return centsSeparator
    }

    fun getDecimalString(value: Double, decimalPlaces: Int): String{
        return when (decimalPlaces) {
            0 -> {
                DecimalFormat(
                    "#",
                    DecimalFormatSymbols(Locale.getDefault())
                ).format(value)
            }
            1 -> {
                DecimalFormat(
                    "#.0",
                    DecimalFormatSymbols(Locale.getDefault())
                ).format(value)
            }
            2 -> {
                DecimalFormat(
                    "#.00",
                    DecimalFormatSymbols(Locale.getDefault())
                ).format(value)
            }
            3 -> {
                DecimalFormat(
                    "#.000",
                    DecimalFormatSymbols(Locale.getDefault())
                ).format(value)
            }
            else -> {
                DecimalFormat(
                    "#.000",
                    DecimalFormatSymbols(Locale.getDefault())
                ).format(value)
            }
        }
    }
}