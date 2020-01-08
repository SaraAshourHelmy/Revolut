package com.ltmtlu.revolut.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ltmtlu.revolut.R
import com.ltmtlu.revolut.data.model.Currency
import com.ltmtlu.revolut.databinding.CurrencyItemViewBinding
import com.ltmtlu.revolut.ui.viewmodel.RateModel

class CurrencyRateAdapter(
    val currencies: ArrayList<RateModel>,
    val changeBase: (Currency) -> Unit
) :
    RecyclerView.Adapter<CurrencyRateAdapter.RateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = DataBindingUtil.inflate<CurrencyItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.currency_item_view,
            parent, false
        )

        return RateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(currencies[position], position)

    }


    fun moveItemTop(position: Int) {
        val rate = RateModel(currencies[position].currency, currencies[position].rate)
        currencies.removeAt(position)
        currencies.add(0, rate)
        notifyDataSetChanged()
        changeBase(currencies[position].currency)
    }

    inner class RateViewHolder(val binding: CurrencyItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            rateModel: RateModel,
            position: Int
        ) {
            binding.currencyName = rateModel.currency
            binding.rate = rateModel.rate.toString()
            binding.root.setOnClickListener {
                moveItemTop(position)
            }

//            binding.rateEditText.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//
//                }
//
//                override fun onTextChanged(
//                    s: CharSequence?,
//                    start: Int, before: Int,
//                    count: Int
//                ) {
//
//                }
//            })

        }
    }
}