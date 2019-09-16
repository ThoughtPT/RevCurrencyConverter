package ric.silva.revsample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ric.silva.revsample.ConverterApplication
import ric.silva.revsample.R
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.enums.ISOCurrencyCodes
import ric.silva.revsample.view.adapters.ConvertAdapter
import androidx.recyclerview.widget.SimpleItemAnimator


class TabConverterFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var rvConvertedItems: RecyclerView
    private lateinit var converAdapter: ConvertAdapter
    private var ignoreUpdate = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab_converter, container, false)
        rvConvertedItems = rootView.findViewById(R.id.rv_convertedItems)

        return rootView
    }

    fun setupRecyclerView(list: List<CurrencyItem>) {
        if (this::converAdapter.isInitialized && this::rvConvertedItems.isInitialized){
            updateList(list)
        } else {
            converAdapter = ConvertAdapter(requireActivity(), ArrayList(list),
                callback = {
                    changeSelectedBaseCurrency(it)
                })
            converAdapter.setHasStableIds(true)
            rvConvertedItems.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = converAdapter
            }
            (rvConvertedItems.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun updateList(list: List<CurrencyItem>){
        if(!ignoreUpdate) {
            converAdapter.update(ArrayList(list))
        }
    }

    private fun changeSelectedBaseCurrency(it: String) {
        ignoreUpdate = true
        stopSchedule(it)
        ignoreUpdate = false
    }

    private fun stopSchedule(it: String) {
        ConverterApplication.instance?.cancelPendingRequests()
        (requireActivity() as ConvertRateActivity).stopSchedule()
        ConvertRateActivity.selectedCurrency = ISOCurrencyCodes.getCurrency(it)
        (requireActivity() as ConvertRateActivity).startScheduleExecutor{}
    }
}