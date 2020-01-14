package com.ltmtlu.revolut.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltmtlu.revolut.data.repository.CurrencyRateRepository

class CurrencyRateViewModelFactory(
    private val currencyRateRepo: CurrencyRateRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRateViewModel::class.java)) {
            return CurrencyRateViewModel(currencyRateRepo) as T
        } else {
            throw  IllegalArgumentException("casting issue")
        }
    }
}