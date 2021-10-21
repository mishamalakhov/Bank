package android.malakhov.bank.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

import kotlin.collections.ArrayList


@Root(strict = false, name = "DailyExRates ")
data class DailyExRates constructor(

    @field:ElementList(inline = true)
    var Currency: ArrayList<Currency>? = null,

)
