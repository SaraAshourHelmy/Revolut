package com.ltmtlu.revolut.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ltmtlu.revolut.data.backendconfig.RevolutApi
import com.ltmtlu.revolut.data.model.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AllRatesViewModel : ViewModel() {

    private var _currenciesLiveData = MutableLiveData<ArrayList<RateModel>>()
    val currenciesLiveData: LiveData<ArrayList<RateModel>>
        get() = _currenciesLiveData

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    private var baseCurrency = Currency.EUR.name

    private var _amountLiveDate = MutableLiveData<Float>()
    val amountLiveData: LiveData<Float>
        get() = _amountLiveDate

    private var _hasProgressVisible = MutableLiveData<Boolean>()
    val hasProgressVisible: LiveData<Boolean>
        get() = _hasProgressVisible

    private val timer = Timer()

    init {
        getCurrencyRate()
        _hasProgressVisible.value = true
    }

    private fun getCurrencyRate() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    scope.launch {
                        val response = RevolutApi.revolutApiService.getRates(baseCurrency).await()
                        val rate = response.rates
                        convertCurrency(rate)
                        _hasProgressVisible.value = false
                    }
                }
            },
            0, 1000
        )
    }

    private fun convertCurrency(rate: Map<String, Float>) {
        var currencies = ArrayList<RateModel>()
        currencies.add(RateModel(baseCurrency, 1f))
        currencies.addAll(rate.map { RateModel(it.key, it.value) })
        _currenciesLiveData.value = currencies
    }

    fun checkBaseCurrency(base: String, amount: Float) {
        if (base.equals(baseCurrency)) {
            _amountLiveDate.value = amount
        } else {
            baseCurrency = base
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}