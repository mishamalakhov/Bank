package android.malakhov.bank.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingsEntity(
    @PrimaryKey
    var CharCode: String = "",
    var Scale: Int? = null,
    var Name: String? = null,
    var switchCheck: Boolean? = null
)
