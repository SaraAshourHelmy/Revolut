package com.ltmtlu.revolut.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ltmtlu.revolut.R

open class BaseFragment : Fragment() {

    open fun showError(view: View) {
        Snackbar.make(view, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
    }
}