package com.ltmtlu.revolut.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.ltmtlu.revolut.databinding.AllRatesFragmentBinding
import com.ltmtlu.revolut.ui.item.CurrencyRateAdapter
import com.ltmtlu.revolut.ui.viewmodel.AllRatesViewModel
import kotlinx.android.synthetic.main.all_rates_fragment.*


class AllRatesFragment : Fragment() {


    private val viewModel: AllRatesViewModel by lazy {
        ViewModelProviders.of(this).get(AllRatesViewModel::class.java)
    }
    private lateinit var binding: AllRatesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllRatesFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.rateViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        var adapter = CurrencyRateAdapter { currency, amount ->
            viewModel.checkBaseCurrency(currency, amount)
        }
        allRatesRecyclerView.adapter = adapter

        viewModel.currenciesLiveData.observe(this, Observer { currencies ->
            adapter.updateCurrencyRate(currencies)

        })

        viewModel.amountLiveData.observe(this, Observer {
            adapter.updateAmount(it)
        })

        viewModel.hasError.observe(this, Observer {
            if (it) showError()
        })
    }

    private fun showError() {
        Snackbar.make(parentLayout, "Error!", Snackbar.LENGTH_SHORT).show()
        viewModel.resetError()
    }
}
