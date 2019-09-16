package ric.silva.revsample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import ric.silva.revsample.ConverterApplication
import ric.silva.revsample.R
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.enums.ISOCurrencyCodes
import ric.silva.revsample.view.adapters.RateAdapter

class TabRatesFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var rvRatesItems: RecyclerView
    private lateinit var rateAdapter: RateAdapter
    private var ignoreUpdate = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab_rates, container, false)
        rvRatesItems = rootView.findViewById(R.id.rv_rateItems)

        return rootView
    }

    fun setupRecyclerView(list: List<CurrencyItem>) {
        if (this::rateAdapter.isInitialized && this::rvRatesItems.isInitialized){
            updateList(list)
        } else {
            rateAdapter = RateAdapter(requireActivity(), ArrayList(list)) {
                changeSelectedBaseCurrency(it)
            }
            rateAdapter.setHasStableIds(true)
            rvRatesItems.layoutManager = LinearLayoutManager(activity)
            rvRatesItems.adapter = rateAdapter
            (rvRatesItems.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun updateList(list: List<CurrencyItem>){
        if(!ignoreUpdate)
            rateAdapter.update(ArrayList(list))
    }

    private fun changeSelectedBaseCurrency(it: String) {
        ignoreUpdate = true
        ConverterApplication.instance?.cancelPendingRequests()
        (requireActivity() as ConvertRateActivity).stopSchedule()
        ConvertRateActivity.selectedCurrency = ISOCurrencyCodes.getCurrency(it)
        (requireActivity() as ConvertRateActivity).startScheduleExecutor{}
        ignoreUpdate = false
    }
}