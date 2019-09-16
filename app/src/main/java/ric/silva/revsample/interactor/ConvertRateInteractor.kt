package ric.silva.revsample.interactor

import org.json.JSONObject
import ric.silva.revsample.ConverterApplication
import ric.silva.revsample.ConverterContract
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.enums.ISOCurrencyCodes
import ric.silva.revsample.repositories.APIControl
import ric.silva.revsample.repositories.WebRequester
import ric.silva.revsample.utils.ConnectivityHelper
import ric.silva.revsample.utils.PreferenceHelper
import ric.silva.revsample.utils.PreferenceHelper.requestKey

class ConvertRateInteractor : ConverterContract.Interactor{
    override fun getRateFromRepository(ISOCodeBase: String, callback: (response: ArrayList<CurrencyItem>) -> Unit) {
        if(ConnectivityHelper.isConnected(ConverterApplication.instance!!.applicationContext)) {
            val service = WebRequester()
            val apiController = APIControl(service)
            val params = JSONObject()
            apiController.get(ISOCodeBase, params) { response ->
                response.let { apiResponse ->
                    PreferenceHelper.setStringPreference(
                        ConverterApplication.instance!!.applicationContext,
                        requestKey,
                        apiResponse.toString()
                    )

                    apiResponse?.let { callback.invoke(populateList(ISOCodeBase, it)) }
                }
            }
        } else {
            val savedResponse = PreferenceHelper.getStringPreference(
                ConverterApplication.instance!!.applicationContext, requestKey)
            val jsonResponse = JSONObject(savedResponse)
            callback.invoke(populateList(ISOCodeBase,jsonResponse))
        }
    }

    private fun populateList(ISOCodeBase: String, response: JSONObject): ArrayList<CurrencyItem> {
        var currencyRateList = ArrayList<CurrencyItem>()
        val rates = response.getJSONObject("rates")
        val keys = rates?.keys()
        while (keys?.hasNext() == true) {
            val countryCode = keys.next()
            val rate = rates.getDouble(countryCode)
            val currencyItem = countryCode?.let { it1 ->
                CurrencyItem(it1, rate, ISOCurrencyCodes.getCurrency(ISOCodeBase)).apply {
                    flagResourceID = ISOCurrencyCodes.getCurrencyFlagID(countryCode)
                    name = ISOCurrencyCodes.getCurrencyName(countryCode)
                    decimalsPlaces = ISOCurrencyCodes.getCurrencyDecimalPlaces(countryCode)
                }
            }

            currencyItem?.let { it1 ->
                currencyRateList.add(it1)
            }
        }
        currencyRateList = ArrayList(currencyRateList.sortedWith(compareBy { it.ISOCode }))
        if (!checkBasePresentInList(currencyRateList, ISOCodeBase))
            addBaseToList(currencyRateList, ISOCodeBase)
        return ArrayList(currencyRateList)
    }

    private fun checkBasePresentInList(currencyRateList: ArrayList<CurrencyItem>, ISOCodeBase: String): Boolean{
        return currencyRateList.any {
            it.ISOCode == ISOCodeBase
        }
    }

    private fun addBaseToList(currencyRateList: ArrayList<CurrencyItem>, ISOCodeBase: String) {
        currencyRateList.add(0,CurrencyItem(ISOCodeBase,1.0, ISOCurrencyCodes.getCurrency(ISOCodeBase)).apply {
            flagResourceID = ISOCurrencyCodes.getCurrencyFlagID(ISOCodeBase)
            name = ISOCurrencyCodes.getCurrencyName(ISOCodeBase)
            decimalsPlaces = ISOCurrencyCodes.getCurrencyDecimalPlaces(ISOCodeBase)
        })
    }
}