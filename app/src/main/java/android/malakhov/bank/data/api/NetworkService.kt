package android.malakhov.bank.data.api

import android.malakhov.bank.data.api.currency.CurrencyApi
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class NetworkService {

    private var mRetrofit: Retrofit? = null

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    companion object{
        private val BASE_URL = "https://www.nbrb.by/Services/"
        private var mInstance: NetworkService? = null

        fun getInstance(): NetworkService? {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }
    }

    fun getCurrencyApi(): CurrencyApi? {
        return mRetrofit!!.create(CurrencyApi::class.java)
    }
}