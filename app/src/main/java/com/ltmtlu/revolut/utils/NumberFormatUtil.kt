package com.ltmtlu.revolut.utils

import java.text.DecimalFormat

object NumberFormatUtil {
    fun formatFloatNumber(number: Float): String {
        val df = DecimalFormat("#.##")
        return df.format(number)
    }
}