package android.malakhov.bank.ui.settings.recycler

import android.malakhov.bank.R
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.databinding.SettingsRecyclerItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var list: ArrayList<SettingsEntity>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: SettingsRecyclerItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.settings_recycler_item,
                parent,
                false
            )
        return MyViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.switchCheck.setOnClickListener{
            list[position].switchCheck = holder.switchCheck.isChecked
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getSettingsList():ArrayList<SettingsEntity>{
        return list
    }
}