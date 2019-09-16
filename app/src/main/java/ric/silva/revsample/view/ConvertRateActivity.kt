package ric.silva.revsample.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import ric.silva.revsample.BaseActivity
import ric.silva.revsample.ConverterContract
import ric.silva.revsample.R
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.enums.ISOCurrencyCodes
import ric.silva.revsample.presenter.ConvertRatePresenter
import ric.silva.revsample.utils.NetworkChangeReceiver
import ric.silva.revsample.view.customListeners.setSingleOnClickListener


class ConvertRateActivity : BaseActivity(), ConverterContract.View {
    private var presenter: ConverterContract.Presenter? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    private lateinit var adapter: TabAdapter
    private lateinit var fragmentRates: TabRatesFragment
    private lateinit var fragmentConvert: TabConverterFragment
    private lateinit var toolbarBack: ImageView

    companion object {
        var selectedCurrency = ISOCurrencyCodes.EUR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarTitle.text = getString(R.string.rateAndConversionTitle)
        toolbarBack = findViewById(R.id.toolbarBack)

        presenter = ConvertRatePresenter(this)
        networkChangeReceiver = NetworkChangeReceiver { isConnected ->
            if (isConnected) {
                startScheduleExecutor {
                    presenter?.getRates(ConvertRateActivity.selectedCurrency.name)
                }
            } else {
                stopSchedule()
            }
        }

        setupViewPager()
        setupBackButton()
        startScheduleExecutor {
            presenter?.getRates(ConvertRateActivity.selectedCurrency.name)
        }
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        applicationContext.registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unregisterReceiver(networkChangeReceiver)
    }

    private fun setupBackButton() {
        toolbarBack.setSingleOnClickListener {
            this.finish()
        }
    }

    private fun setupViewPager(){
        adapter = TabAdapter(supportFragmentManager)

        fragmentRates = TabRatesFragment(R.layout.fragment_tab_rates)
        fragmentConvert = TabConverterFragment(R.layout.fragment_tab_converter)

        adapter.addFragment(fragmentRates, getString(R.string.tabAllRatesTitle))
        adapter.addFragment(fragmentConvert, getString(R.string.tabConverterTitle))

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun updateRates(list: List<CurrencyItem>) {
        loadingParent.visibility = View.INVISIBLE
        fragmentRates.setupRecyclerView(list)
        fragmentConvert.setupRecyclerView(list)
    }
}
