package ric.silva.revsample.entity

import com.google.gson.annotations.SerializedName
import ric.silva.revsample.enums.ISOCurrencyCodes

class CurrencyItem (
    @SerializedName("ISOCode")
    val ISOCode: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName ("currencyUsedInRate")
    val currencyUsedInRate: ISOCurrencyCodes

){
    @SerializedName("flagImageUrl")
    var flagResourceID: Int = -1

    @SerializedName("name")
    var name: String = ""

    @SerializedName("decimals")
    var decimalsPlaces = 2
}