package com.ltmtlu.revolut.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ltmtlu.revolut.data.backendconfig.RevolutApi
import com.ltmtlu.revolut.data.model.Currency
import com.ltmtlu.revolut.data.model.Rate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AllRatesViewModel : ViewModel() {

    private var _currenciesLiveData = MutableLiveData<ArrayList<RateModel>>()
    val currenciesLiveData: LiveData<ArrayList<RateModel>>
        get() = _currenciesLiveData

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    private var baseCurrency = Currency.EUR

    init {
        getAllRates(baseCurrency.name)
    }

    private fun getAllRates(base: String) {
        scope.launch {
            val response = RevolutApi.revolutApiService.getRates(base).await()
            val rate = response.rates
            Log.e("currencies", rate.toString())
            convertCurrency(rate)
        }
    }

    private fun convertCurrency(rate: Rate) {
        var currencies = ArrayList<RateModel>()

        currencies.add(RateModel(Currency.EUR, rate.eur))
        currencies.add(RateModel(Currency.AUD, rate.aud))
        currencies.add(RateModel(Currency.BGN, rate.bgn))
        currencies.add(RateModel(Currency.BRL, rate.brl))
        currencies.add(RateModel(Currency.CAD, rate.cad))
        currencies.add(RateModel(Currency.CHF, rate.chf))
        currencies.add(RateModel(Currency.CNY, rate.cny))
        currencies.add(RateModel(Currency.CZK, rate.czk))
        currencies.add(RateModel(Currency.DKK, rate.dkk))
        currencies.add(RateModel(Currency.GBP, rate.gbp))
        currencies.add(RateModel(Currency.HKD, rate.hkd))
        currencies.add(RateModel(Currency.HRK, rate.hrk))
        currencies.add(RateModel(Currency.HUF, rate.huf))
        currencies.add(RateModel(Currency.IDR, rate.idr))
        currencies.add(RateModel(Currency.ILS, rate.ils))
        currencies.add(RateModel(Currency.INR, rate.inr))
        currencies.add(RateModel(Currency.ISK, rate.isk))
        currencies.add(RateModel(Currency.JPY, rate.jpy))
        currencies.add(RateModel(Currency.KRW, rate.krw))
        currencies.add(RateModel(Currency.MXN, rate.mxn))
        currencies.add(RateModel(Currency.MYR, rate.myr))
        currencies.add(RateModel(Currency.NOK, rate.nok))
        currencies.add(RateModel(Currency.NZD, rate.nzd))
        currencies.add(RateModel(Currency.PHP, rate.php))
        currencies.add(RateModel(Currency.PLN, rate.pln))
        currencies.add(RateModel(Currency.RON, rate.ron))
        currencies.add(RateModel(Currency.RUB, rate.rub))
        currencies.add(RateModel(Currency.SEK, rate.sek))
        currencies.add(RateModel(Currency.SGD, rate.sgd))
        currencies.add(RateModel(Currency.THB, rate.thb))
        currencies.add(RateModel(Currency.TRY, rate.tryCurrency))
        currencies.add(RateModel(Currency.USD, rate.usd))
        currencies.add(RateModel(Currency.ZAR, rate.zar))
        _currenciesLiveData.value = checkBaseCurrency(currencies)

        _currenciesLiveData.value = currencies
    }

    private fun checkBaseCurrency(currencies: ArrayList<RateModel>): ArrayList<RateModel> {
        var updateCurrencies = currencies.filter { rateModel -> rateModel.currency == baseCurrency }
        (updateCurrencies as ArrayList).add(0, RateModel(baseCurrency, 1f))
        return updateCurrencies
    }
}