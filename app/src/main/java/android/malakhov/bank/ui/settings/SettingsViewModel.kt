package android.malakhov.bank.ui.settings

import android.content.Context
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.data.repository.SqLiteDB
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {


    //get settings from settings database
    suspend fun getSettings(context: Context): ArrayList<SettingsEntity>{

        val list = ArrayList<SettingsEntity>()
        GlobalScope.async {
            list.addAll(SqLiteDB.getAppDB(context)?.settingsDao()?.getAll()!!)
        }.await()

        return list
    }

    suspend fun saveSettings(list: ArrayList<SettingsEntity>, context: Context){
        GlobalScope.async {
            SqLiteDB.getAppDB(context)?.settingsDao()?.clearTable()
        }.await()

        GlobalScope.launch {
            list.forEach {
                SqLiteDB.getAppDB(context)?.settingsDao()?.insert(it)
            }
        }
    }
}