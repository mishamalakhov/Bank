package android.malakhov.bank.data.repository

import android.content.Context
import android.malakhov.bank.data.model.CurrencyForDB
import android.malakhov.bank.data.model.SettingsEntity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyForDB::class, SettingsEntity::class], version = 1)
abstract class SqLiteDB : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao?
    abstract fun settingsDao(): SettingsDao?

    companion object{
        private var db: SqLiteDB? = null

        //SingleTone
        fun getAppDB(context: Context): SqLiteDB? {
            return if (db == null) {
                Room.databaseBuilder(
                    context,
                    SqLiteDB::class.java, "database"
                ).build()
            } else {
                db
            }
        }
    }
}