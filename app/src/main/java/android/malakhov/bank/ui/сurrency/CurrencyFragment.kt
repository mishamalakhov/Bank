package android.malakhov.bank.ui.сurrency

import android.malakhov.bank.R
import android.malakhov.bank.data.model.CurrencyForDB
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.databinding.FragmentCurrencyBinding
import android.malakhov.bank.ui.MainActivity
import android.malakhov.bank.ui.сurrency.recycler.MyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class CurrencyFragment : Fragment() {

    private lateinit var viewModel: CurrencyViewModel // ViewModel
    private var list = ArrayList<CurrencyForDB>()    //List of Currencies
    private var recycler: RecyclerView? = null  //recycler
    private var adapter: MyAdapter? = null      //adapter
    private var progressBar: ProgressBar? = null    //progressBar
    private var dates = ArrayList<String>()     //List consist of start date and end date
    private var settings = ArrayList<SettingsEntity>()  //Settings list from SettingsFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCurrencyBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)


        init(binding)

        //prepare recycler
        recycler?.layoutManager = LinearLayoutManager(activity)
        recycler?.adapter = adapter

        //get list of currencies
        GlobalScope.launch(Dispatchers.Main) {
            list.addAll(viewModel.getCurrencies(requireActivity()))
            dates.addAll(viewModel.getDates())
            progressBar?.visibility = View.INVISIBLE
            adapter?.notifyDataSetChanged()
            (activity as MainActivity).setSettingBtnVisible()
        }
        return binding.root
    }


    //Update UI after changing settings
    fun updateUi() {
        if (settings.size > 0) {

            GlobalScope.launch(Dispatchers.Main) {
                list.clear()
                adapter?.notifyDataSetChanged()
                list.addAll(viewModel.getCurrenciesBySettings(settings, requireActivity()))
                adapter?.notifyDataSetChanged()
            }
        }
    }


    //init all variables
    private fun init(binding: FragmentCurrencyBinding) {
        recycler = binding.recycler
        adapter = MyAdapter(list, dates)
        progressBar = binding.progressBar
        viewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)
    }

    fun setSettings(list: ArrayList<SettingsEntity>) {
        settings.clear()
        settings.addAll(list)
    }
}


