package com.ltmtlu.revolut.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ltmtlu.revolut.R
import com.ltmtlu.revolut.ui.item.CurrencyRateAdapter
import com.ltmtlu.revolut.ui.viewmodel.AllRatesViewModel
import kotlinx.android.synthetic.main.all_rates_fragment.*


class AllRatesFragment : Fragment() {

    lateinit var viewModel: AllRatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.all_rates_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllRatesViewModel::class.java)
        observeData()
    }

    private fun observeData() {
        viewModel.currenciesLiveData.observe(this, Observer { currencies ->
            allRatesRecyclerView.adapter = CurrencyRateAdapter(currencies){
                allRatesRecyclerView.scrollToPosition(0)
            }
        })
    }

}
