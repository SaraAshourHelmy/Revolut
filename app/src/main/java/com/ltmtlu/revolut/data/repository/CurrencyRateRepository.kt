package com.ltmtlu.revolut.data.repository

import com.ltmtlu.revolut.data.backendconfig.RevolutApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyRateRepository {

    suspend fun fetchCurrencyRate(baseCurrency: String): Map<String, Float> {
        return withContext(Dispatchers.IO)
        {
            val response = RevolutApi.revolutApiService.getRates(baseCurrency).await()
            response.rates
        }
    }
}