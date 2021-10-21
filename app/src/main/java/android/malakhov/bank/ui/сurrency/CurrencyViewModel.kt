package android.malakhov.bank.ui.сurrency

import android.content.Context
import android.malakhov.bank.data.api.NetworkService
import android.malakhov.bank.data.model.CurrencyForDB
import android.malakhov.bank.data.model.DailyExRates
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.data.repository.SqLiteDB
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CurrencyViewModel : ViewModel() {

    private var ratesStart: DailyExRates? = null
    private var ratesEnd: DailyExRates? = null
    var dateStart: String? = null
    var dateEnd: String? = null



    suspend fun getCurrenciesBySettings(
        list: ArrayList<SettingsEntity>,
        context: Context
    ): ArrayList<CurrencyForDB> {

        val currList =
            ArrayList<CurrencyForDB>()  //List of currencies, which will shows in recycler

        GlobalScope.async {
            list.forEach {
                if (it.switchCheck == true) {
                    currList.add(
                        SqLiteDB.getAppDB(context)?.currencyDao()
                            ?.getCurrencyBySettings(it.CharCode)!!
                    )
                }
            }
        }.await()
        return currList
    }


    //Get Currencies from SqLite
    suspend fun getCurrencies(fragment: CurrencyFragment): ArrayList<CurrencyForDB> {
        val currList =
            ArrayList<CurrencyForDB>()  //List of currencies, which will shows in recycler


        val context = fragment.context

        GlobalScope.async {

            loadCurrenciesFromNetwork(fragment, 0)

            //IF we get error from server so we return empty list
            if(ratesStart != null || ratesEnd != null){
                //Get currencies from db according to settings from SettingsDB.
                //If switch = true, so we get this currency from DB
                val settingsList = ArrayList<SettingsEntity>()
                settingsList.addAll(SqLiteDB.getAppDB(context!!)?.settingsDao()?.getAll()!!)
                currList.addAll(getCurrenciesBySettings(settingsList, context))
            }
        }.await()
        return currList
    }

    //Get Currencies from Api
    private suspend fun loadCurrenciesFromNetwork(fragment: CurrencyFragment, daysCount: Int) {

        val sdf = SimpleDateFormat("MM/dd/yyyy")    //date format
        val cal = Calendar.getInstance()    //calendar

        //get Current date and current date + 1
        cal.add(Calendar.DATE, daysCount)
        dateStart = sdf.format(Date(cal.timeInMillis))
        cal.add(Calendar.DATE, 1)
        dateEnd = sdf.format(Date(cal.timeInMillis))


        GlobalScope.async {
            try {
                ratesEnd = NetworkService.getInstance()
                    ?.getCurrencyApi()
                    ?.getList(dateEnd!!)
                    ?.execute()?.body()

                //get today or yesterday info
                try {
                    ratesStart = NetworkService.getInstance()
                        ?.getCurrencyApi()
                        ?.getList(dateStart!!)
                        ?.execute()?.body()
                    loadCurrenciesToDB(fragment.requireContext())
                } catch (e: Exception) { //Show error message from service
                    showError(fragment)
                }
            } catch (e: Exception) {
                //if service don't have info about tomorrow currencies
                // so we try to get yesterday - today info
                if (e.toString().contains("Unable to satisfy")) {
                    loadCurrenciesFromNetwork(fragment, -1)
                }else{
                    showError(fragment)
                }
            }
        }.await()
    }

    //Show message error if service give us error response
    private fun showError(fragment: CurrencyFragment){
        GlobalScope.launch(Dispatchers.Main) { fragment.showError() }
    }


    //Load Currencies to sqLite
    private suspend fun loadCurrenciesToDB(context: Context) {

        GlobalScope.async {

            //Set default settings(usd, rub, eur) when Currency database is empty
            if (SqLiteDB.getAppDB(context)?.currencyDao()?.getCount() == 0)
                setDefaultSettingsToDB(context)

            for (i in 0 until ratesEnd?.Currency?.size!!) {
                val currStart = ratesStart?.Currency?.get(i)
                val currEnd = ratesEnd?.Currency?.get(i)

                val curr = CurrencyForDB(
                    currStart?.CharCode!!, currStart.Scale, currStart.Name,
                    dateStart, dateEnd, currStart.Rate, currEnd?.Rate
                )

                if (SqLiteDB.getAppDB(context)?.currencyDao()?.isObjectExist(curr.CharCode) == 1)
                    SqLiteDB.getAppDB(context)?.currencyDao()?.update(curr)
                else {
                    SqLiteDB.getAppDB(context)?.currencyDao()?.insert(curr)
                }
            }
        }.await()
    }

    //Set all settings in Settings database if Currency database is empty
    //and make usd, rub, eur default(switch = true, others = false)
    private fun setDefaultSettingsToDB(context: Context) {

        ratesEnd?.Currency?.forEach {
            val setting = SettingsEntity(it.CharCode, it.Scale, it.Name)
            setting.switchCheck = it.CharCode == "EUR" ||
                    it.CharCode == "RUB" ||
                    it.CharCode == "USD"
            SqLiteDB.getAppDB(context)?.settingsDao()?.insert(setting)
        }
    }

    fun getDates(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(dateStart!!)
        list.add(dateEnd!!)
        return list
    }
}