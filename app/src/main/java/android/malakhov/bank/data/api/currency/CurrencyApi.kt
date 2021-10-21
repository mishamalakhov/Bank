package android.malakhov.bank.data.api.currency

import android.malakhov.bank.data.model.DailyExRates
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {
    @GET("XmlExRates.aspx")
    fun getList(@Query("ondate") date: String): Call<DailyExRates?>?
}