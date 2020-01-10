package com.ltmtlu.revolut.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected var _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean>
        get() = _hasError

    protected var _hasProgressVisible = MutableLiveData<Boolean>()
    val hasProgressVisible: LiveData<Boolean>
        get() = _hasProgressVisible

    init {
        _hasProgressVisible.value = true
        _hasError.value = false
    }

    fun resetError() {
        _hasError.value = false
    }
}