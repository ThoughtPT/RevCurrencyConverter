package ric.silva.revsample.presenter

import ric.silva.revsample.ConverterContract
import ric.silva.revsample.interactor.ConvertRateInteractor
import ric.silva.revsample.router.ConvertRateRouter

class ConvertRatePresenter(private var view: ConverterContract.View?): ConverterContract.Presenter {
    private var interactor: ConverterContract.Interactor? = ConvertRateInteractor()
    private var router: ConverterContract.Router? = ConvertRateRouter(view)

    override fun getRates(ISOCodeReference: String) {
        interactor?.getRateFromRepository(ISOCodeReference) { list ->
            view?.updateRates(list)
        }
    }

    override fun goBack() {
        router?.goBack()
    }
}