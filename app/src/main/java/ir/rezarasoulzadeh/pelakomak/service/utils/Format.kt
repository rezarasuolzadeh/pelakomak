package ir.rezarasoulzadeh.pelakomak.service.utils

import java.text.NumberFormat
import java.util.*

class Format {

    fun priceFormat(price: Int): String {
        return NumberFormat
            .getNumberInstance(Locale.US)
            .format(price / 10)
            .toString()
            .plus(" ")
            .plus("تومان")
    }

}