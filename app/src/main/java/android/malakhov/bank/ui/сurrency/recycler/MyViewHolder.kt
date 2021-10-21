package android.malakhov.bank.ui.сurrency.recycler

import android.malakhov.bank.data.model.CurrencyForDB
import android.malakhov.bank.databinding.CurrencyRecyclerItemBinding
import android.malakhov.bank.databinding.RecyclerDateHeaderBinding
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View, var binding: Any) :
    RecyclerView.ViewHolder(itemView) {


    fun onBind(currency: CurrencyForDB?, dates: ArrayList<String>?) {

        if (binding is RecyclerDateHeaderBinding) {
            val bind = binding as RecyclerDateHeaderBinding
            if (dates?.size != 0) {
                bind.dateStart.text = dates?.get(0)?.replace('/', '.')
                bind.dateEnd.text = dates?.get(1)?.replace('/', '.')
            }
        } else {
            val bind = binding as CurrencyRecyclerItemBinding
            bind.currency.text = currency?.CharCode
            bind.amount.text = currency?.Scale.toString() + " " + currency?.Name
            bind.rateStart.text = currency?.rateStart.toString()
            bind.rateEnd.text = currency?.rateEnd.toString()
        }
    }
}