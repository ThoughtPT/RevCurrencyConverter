package ric.silva.revsample.view.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ric.silva.revsample.R
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.utils.DecimalUtils
import ric.silva.revsample.view.customListeners.setSingleOnClickListener


class ConvertAdapter(
    private val context: Context,
    private var list: ArrayList<CurrencyItem>,
    private var callback: (selectedISOCode: String) -> Unit
) : RecyclerView.Adapter<ConvertAdapter.ViewHolder>() {

    private var amountToConvert: Double = 100.toDouble()
    private var updateAll = false

    inner class ViewHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v), View.OnClickListener {
        var ivConvertedFlag = v.findViewById(R.id.ivConvertedFlag) as ImageView
        var etValue = v.findViewById(R.id.etValue) as EditText
        var tvConvertedCurrencyCode = v.findViewById(R.id.tvConvertedCurrencyCode) as TextView
        var tvConvertedCurrencyName = v.findViewById(R.id.tvConvertedCurrencyName) as TextView
        var textArea = v.findViewById(R.id.textArea) as LinearLayout

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view1 = LayoutInflater.from(context).inflate(
            R.layout.adapter_converted_list, parent, false)

        return ViewHolder(view1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(list[position].flagResourceID)
            .centerCrop()
            .into(holder.ivConvertedFlag)

        holder.tvConvertedCurrencyCode.text = list[position].ISOCode
        holder.tvConvertedCurrencyName.text = list[position].name
        if (position != 0) {
            val value = calculateRate(position)
            var amount = ""
            if (value != 0.toDouble()) {
                amount = DecimalUtils.getDecimalString(value, list[position].decimalsPlaces)
                if (amount.first() == '.')
                    amount = "0$amount"
            }
            holder.etValue.setText(amount)
        } else {
            holder.etValue.setText(amountToConvert.toString())

            holder.etValue.addTextChangedListener {
                amountToConvert = if (it.toString().isNotEmpty() && it.toString().first() != '.') {
                    it.toString().toDouble()
                } else
                    0.toDouble()
            }
        }

        holder.textArea.setSingleOnClickListener {
            updateAll = true
            callback.invoke(list[position].ISOCode)
            moveSelectedToFirstPosition(position)
        }
    }

    private fun calculateRate(position: Int) : Double{
        if (position == 0) return amountToConvert
        return list[position].rate * amountToConvert
    }

    private fun moveSelectedToFirstPosition(position: Int) {
        val tempItem = list[position]
        list.removeAt(position)
        val sortedList = ArrayList(list.sortedWith(compareBy { it.ISOCode }))
        sortedList.add(0, tempItem)
        update(sortedList)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(list: ArrayList<CurrencyItem>) {
        this.list = ArrayList()
        this.list = list
        if (updateAll){
            setDataChanged()
        } else {
            for (i in 1..list.lastIndex) {
                notifyItemChange(i)
            }
        }
    }

    private fun notifyItemChange(position: Int){
        notifyItemChanged(position)
    }

    private fun setDataChanged() {
        updateAll = false
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }
}