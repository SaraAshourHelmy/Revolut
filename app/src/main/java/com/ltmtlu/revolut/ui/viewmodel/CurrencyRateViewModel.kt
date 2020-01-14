package com.ltmtlu.revolut.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ltmtlu.revolut.base.BaseViewModel
import com.ltmtlu.revolut.data.repository.CurrencyRateRepository
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class CurrencyRateViewModel(private val currencyRepository: CurrencyRateRepository) :
    BaseViewModel() {

    private var _currenciesLiveData = MutableLiveData<ArrayList<RateModel>>()
    val currenciesLiveData: LiveData<ArrayList<RateModel>>
        get() = _currenciesLiveData

    private val job = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _hasError.value = true
        _hasProgressVisible.value = false
    }
    private val scope = CoroutineScope(job + Dispatchers.Main + coroutineExceptionHandler)
    private var baseCurrency = "EUR"

    private var _amountLiveDate = MutableLiveData<Float>()
    val amountLiveData: LiveData<Float>
        get() = _amountLiveDate

    private val timer = Timer()

    init {
        getCurrencyRate()
    }

    fun getCurrencyRate() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    scope.launch {
                        val rate = currencyRepository.fetchCurrencyRate(baseCurrency)
                        _currenciesLiveData.value = mapCurrencies(rate)
                        _hasProgressVisible.value = false
                    }
                }
            },
            0, 1000
        )
    }

    fun mapCurrencies(rate: Map<String, Float>): ArrayList<RateModel> {
        var currencies = ArrayList<RateModel>()
        currencies.add(RateModel(baseCurrency, 1f))
        currencies.addAll(rate.map { RateModel(it.key, it.value) })
        return currencies
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