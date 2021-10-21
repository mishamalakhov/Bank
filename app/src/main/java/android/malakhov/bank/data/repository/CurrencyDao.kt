package android.malakhov.bank.data.repository

import android.malakhov.bank.data.model.CurrencyForDB
import androidx.room.*

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM CurrencyForDB")
    suspend fun getAll(): List<CurrencyForDB>

    @Query("SELECT * FROM CurrencyForDB WHERE CharCode =:charCode")
    suspend fun getCurrencyBySettings(charCode: String): CurrencyForDB

    @Insert
    fun insert(currency: CurrencyForDB)

    @Update()
    fun update(currency: CurrencyForDB)

    @Query("SELECT COUNT(*) FROM CurrencyForDB")
    fun getCount(): Int

    @Query("SELECT EXISTS(SELECT * FROM CurrencyForDB WHERE CharCode = :id LIMIT 1)")
    fun isObjectExist(id: String):Int
}