package android.malakhov.bank.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(strict = false, name = "Currency")
data class Currency constructor (
    @field:Element(name = "CharCode", required = false)
    @PrimaryKey
    var CharCode: String = "",
    @field:Element(name = "Scale", required = false)
    var Scale: Int? = null,
    @field:Element(name = "Name", required = false)
    var Name: String? = null,
    @field:Element(name = "Rate", required = false)
    var Rate: Double? = null,
)

@Entity
data class CurrencyForDB(
    @PrimaryKey
    var CharCode: String = "",
    var Scale: Int? = null,
    var Name: String? = null,
    var dateStart: String? = null,
    var dateEnd: String? = null,
    var rateStart: Double? = null,
    var rateEnd: Double? = null,

)
