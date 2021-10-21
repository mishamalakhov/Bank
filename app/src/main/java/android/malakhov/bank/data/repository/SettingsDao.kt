package android.malakhov.bank.data.repository

import android.malakhov.bank.data.model.SettingsEntity
import androidx.room.*

@Dao
interface SettingsDao {

    @Query("SELECT * FROM SettingsEntity")
    suspend fun getAll(): List<SettingsEntity>

    @Insert
    fun insert(setting: SettingsEntity)

    @Update()
    fun update(setting: SettingsEntity)

    @Query("SELECT COUNT(*) FROM SettingsEntity")
    fun getCount(): Int

    @Query("DELETE FROM SettingsEntity")
    fun clearTable()
}