package android.malakhov.bank.ui.сurrency.recycler

import android.malakhov.bank.R
import android.malakhov.bank.data.model.CurrencyForDB
import android.malakhov.bank.databinding.CurrencyRecyclerItemBinding
import android.malakhov.bank.databinding.RecyclerDateHeaderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var list: ArrayList<CurrencyForDB>, val dates: ArrayList<String>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        if (viewType == 0) {
            val binding: RecyclerDateHeaderBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_date_header,
                    parent,
                    false
                )
            return MyViewHolder(binding.root, binding)
        }
        val binding: CurrencyRecyclerItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.currency_recycler_item,
                parent,
                false
            )
        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 0)
            holder.onBind(null, dates)
        else
            holder.onBind(list[position - 1], null)
    }

    //ItemCount = itemCount + 1 because there is a header with index 0
    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}