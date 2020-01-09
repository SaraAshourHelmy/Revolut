package com.ltmtlu.revolut.ui.viewmodel

import com.ltmtlu.revolut.data.model.Currency

data class RateModel(
    val currency: String,
    val rate: Float
) {}