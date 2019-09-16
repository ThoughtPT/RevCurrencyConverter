package ric.silva.revsample.view.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ric.silva.revsample.R
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.view.customListeners.setSingleOnClickListener

class RateAdapter(
    private val context: Context,
    private var list: ArrayList<CurrencyItem>,
    private var callback: (selectedISOCode: String) -> Unit
) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {

    inner class ViewHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v){
        var ivConvertedFlag = v.findViewById(R.id.ivConvertedFlag) as ImageView
        var tvValue = v.findViewById(R.id.tvValue) as TextView
        var tvConvertedCurrencyCode = v.findViewById(R.id.tvConvertedCurrencyCode) as TextView
        var tvConvertedCurrencyName = v.findViewById(R.id.tvConvertedCurrencyName) as TextView
        var textArea = v.findViewById(R.id.textArea) as LinearLayout
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view1 = LayoutInflater.from(context).inflate(
            R.layout.adapter_rate_list, parent, false)

        return ViewHolder(view1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivConvertedFlag.setImageDrawable(ContextCompat.getDrawable(context, list[position].flagResourceID))
        holder.tvConvertedCurrencyCode.text = list[position].ISOCode
        holder.tvConvertedCurrencyName.text = list[position].name
        holder.tvValue.text = list[position].rate.toString()

        holder.textArea.setSingleOnClickListener {
            callback.invoke(list[position].ISOCode)
            moveSelectedToFirstPosition(position)
        }
    }

    private fun moveSelectedToFirstPosition(position: Int){
        val tempItem = list[position]
        list.removeAt(position)
        val sortedList = ArrayList(list.sortedWith(compareBy { it.ISOCode }))
        sortedList.add(0, tempItem)
        update(sortedList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(list: ArrayList<CurrencyItem>) {
        this.list.clear()
        this.list = ArrayList(list)
        setDataChanged()
    }

    private fun setDataChanged() {
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}