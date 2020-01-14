package com.ltmtlu.revolut.ui.viewmodel

data class RateModel(
    val currency: String,
    val rate: Float
) {
    fun getAmountRate(amount: Float): Float {
        return rate * amount
    }
}