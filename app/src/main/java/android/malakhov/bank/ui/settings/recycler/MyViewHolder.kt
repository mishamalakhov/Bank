package android.malakhov.bank.ui.settings.recycler

import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.databinding.SettingsRecyclerItemBinding
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View, var binding: SettingsRecyclerItemBinding) :
    RecyclerView.ViewHolder(itemView) {

    val switchCheck = binding.switch1

    fun onBind(setting: SettingsEntity) {

        binding.currency.text = setting.CharCode
        binding.amount.text = setting?.Scale.toString() + " " + setting?.Name
        switchCheck.isChecked = setting.switchCheck!!

    }
}