package com.ltmtlu.revolut.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("progressVisibility")
fun hasProgressVisible(progress: ProgressBar, hasVisible: Boolean?) {
    hasVisible.let {
        if (it!!) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
    }
}