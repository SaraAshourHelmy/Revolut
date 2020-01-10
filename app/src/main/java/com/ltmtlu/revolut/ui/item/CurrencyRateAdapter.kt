package com.ltmtlu.revolut.ui.item

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ltmtlu.revolut.R
import com.ltmtlu.revolut.databinding.CurrencyRateItemBinding
import com.ltmtlu.revolut.ui.viewmodel.RateModel
import com.ltmtlu.revolut.utils.NumberFormatUtil
import com.ltmtlu.revolut.utils.ResourceUtil
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class CurrencyRateAdapter(
    val checkBaseCurrency: (String, Float, Boolean) -> Unit
) :
    RecyclerView.Adapter<CurrencyRateAdapter.RateViewHolder>() {

    private val currencyNameList = ArrayList<String>()
    private val currencyRateMap = HashMap<String, RateModel>()
    private var amount = 1f

    fun updateCurrencyRate(rates: ArrayList<RateModel>) {
        if (currencyNameList.isEmpty()) {
            currencyNameList.addAll(rates.map { it.currency })
        }

        for (rate in rates) {
            currencyRateMap[rate.currency] = rate
        }

        notifyItemRangeChanged(0, currencyNameList.size - 1, amount)
    }

    private fun rateAtPosition(pos: Int): RateModel {
        return currencyRateMap[currencyNameList[pos]]!!
    }

    fun updateAmount(updatedAmount: Float) {
        amount = updatedAmount
        notifyItemRangeChanged(0, currencyNameList.size - 1, amount)
    }

    override fun getItemCount(): Int {
        return currencyNameList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = DataBindingUtil.inflate<CurrencyRateItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.currency_rate_item,
            parent, false
        )

        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rateAtPosition(position))

    }

    inner class RateViewHolder(val binding: CurrencyRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            rateModel: RateModel
        ) {

            binding.currencyName = rateModel.currency
            val iconName = String.format("ic_%s", rateModel.currency.toLowerCase())
            binding.currencyImageView.setImageResource(
                ResourceUtil.getResourceIcon(
                    iconName,
                    binding.root.context
                )
            )
            if (!binding.rateEditText.isFocused)
                binding.rate = NumberFormatUtil.formatFloatNumber(rateModel.rate * amount)

            binding.rateEditText.onFocusChangeListener = getFocusListener()
            binding.root.setOnClickListener {
                moveToTop()
                checkBaseCurrency("", 1f, true)
            }

            binding.rateEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int, before: Int,
                    count: Int
                ) {
                    if (binding.rateEditText.isFocused) {
                        var newAmount = if (s.isNullOrEmpty()) 0f else s.toString().toFloat()
                        checkBaseCurrency(rateModel.currency, newAmount, false)
                    }
                }
            })
        }

        private fun getFocusListener(): View.OnFocusChangeListener {
            return View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    return@OnFocusChangeListener
                }
                moveToTop()
            }
        }

        private fun moveToTop() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                currencyNameList.removeAt(currentPosition).also {
                    currencyNameList.add(0, it)
                }
                notifyItemMoved(currentPosition, 0)
            }
        }
    }
}
