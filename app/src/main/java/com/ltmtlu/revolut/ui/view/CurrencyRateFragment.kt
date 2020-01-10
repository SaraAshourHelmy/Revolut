package com.ltmtlu.revolut.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ltmtlu.revolut.base.BaseFragment
import com.ltmtlu.revolut.databinding.CurrencyRateFragmentBinding
import com.ltmtlu.revolut.ui.item.CurrencyRateAdapter
import com.ltmtlu.revolut.ui.viewmodel.CurrencyRateViewModel
import kotlinx.android.synthetic.main.currency_rate_fragment.*


class CurrencyRateFragment : BaseFragment() {


    private val viewModel: CurrencyRateViewModel by lazy {
        ViewModelProviders.of(this).get(CurrencyRateViewModel::class.java)
    }
    private lateinit var binding: CurrencyRateFragmentBinding
    private lateinit var adapter: CurrencyRateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrencyRateFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.rateViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        adapter = CurrencyRateAdapter { currency, amount, hasScroll ->
            if (hasScroll) {
                (allRatesRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )
            } else {
                viewModel.checkBaseCurrency(currency, amount)
            }
        }
        allRatesRecyclerView.adapter = adapter
    }

    private fun observeData() {

        viewModel.currenciesLiveData.observe(this, Observer { currencies ->
            adapter.updateCurrencyRate(currencies)

        })

        viewModel.amountLiveData.observe(this, Observer {
            adapter.updateAmount(it)
        })

        viewModel.hasError.observe(this, Observer {
            if (it) showError(parentLayout)
        })
    }

    override fun showError(view: View) {
        super.showError(view)
        viewModel.resetError()
    }
}
