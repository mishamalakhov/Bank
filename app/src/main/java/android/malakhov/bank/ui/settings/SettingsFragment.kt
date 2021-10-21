package android.malakhov.bank.ui.settings

import android.malakhov.bank.R
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.databinding.FragmentSettingsBinding
import android.malakhov.bank.ui.MainActivity
import android.malakhov.bank.ui.settings.recycler.CustomItemTouchHelper
import android.malakhov.bank.ui.settings.recycler.MyAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel // ViewModel
    private var list = ArrayList<SettingsEntity>()    //List of Settings
    private var recycler: RecyclerView? = null  //recycler
    private var adapter: MyAdapter? = null      //adapter
    private var progressBar: ProgressBar? = null    //progressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        init(binding)

        //prepare recycler
        val helper = ItemTouchHelper(CustomItemTouchHelper(this))
        helper.attachToRecyclerView(recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        recycler?.adapter = adapter


        //get list of settings
        GlobalScope.launch(Dispatchers.Main) {
            list.addAll(viewModel.getSettings(requireActivity()))
            progressBar?.visibility = View.INVISIBLE
            adapter?.notifyDataSetChanged()
        }

        return binding.root
    }

    //init all variables
    private fun init(binding: FragmentSettingsBinding) {
        recycler = binding.recycler
        adapter = MyAdapter(list)
        progressBar = binding.progressBar
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    fun swapList(from:Int, to:Int){
        Collections.swap(list, from,to)
        adapter?.notifyItemMoved(from,to)
    }

    fun saveSettings(){
        GlobalScope.launch {
            viewModel.saveSettings(adapter?.getSettingsList()!!, requireActivity())
        }
    }

    override fun onStop() {
        super.onStop()
        saveSettings()
        (activity as MainActivity).setSettings(adapter?.getSettingsList()!!)
    }

}




