package ric.silva.revsample

import ric.silva.revsample.entity.CurrencyItem

interface ConverterContract {
    interface View {
        fun finish()
        fun updateRates(list: List<CurrencyItem>)
    }

    interface Presenter {
        fun getRates(ISOCodeReference: String)
        fun goBack()
    }

    interface Interactor {
        fun getRateFromRepository(ISOCodeBase: String, callback: (response: ArrayList<CurrencyItem>) -> Unit)
    }

    interface Router {
        fun goBack()
    }
}