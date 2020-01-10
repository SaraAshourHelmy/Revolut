package com.ltmtlu.revolut.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ltmtlu.revolut.data.backendconfig.RevolutApi
import com.ltmtlu.revolut.data.model.Currency
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class AllRatesViewModel : ViewModel() {

    private var _currenciesLiveData = MutableLiveData<ArrayList<RateModel>>()
    val currenciesLiveData: LiveData<ArrayList<RateModel>>
        get() = _currenciesLiveData

    private var _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean>
        get() = _hasError

    private val job = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _hasError.value = true
        _hasProgressVisible.value = false
    }
    private val scope = CoroutineScope(job + Dispatchers.Main + coroutineExceptionHandler)

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
        _hasError.value = false
    }

    private fun getCurrencyRate() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    Log.e("CallAPI", baseCurrency)
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

    fun resetError() {
        _hasError.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}