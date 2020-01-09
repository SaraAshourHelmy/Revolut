package com.ltmtlu.revolut.utils

import android.content.Context

object ResourceUtil {
    fun getResourceIcon(name: String, context: Context): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}