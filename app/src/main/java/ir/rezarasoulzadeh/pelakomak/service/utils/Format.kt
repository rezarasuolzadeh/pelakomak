package ir.rezarasoulzadeh.pelakomak.service.utils

import ir.rezarasoulzadeh.pelakomak.model.Foul
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

    fun countFormat(count: Int): String {
        return count
            .toString()
            .plus(" ")
            .plus("مورد خلافی")
    }

    fun plaqueSection(plaque: String): ArrayList<String> {
        val section = ArrayList<String>(4)
        section.add("${plaque[4]}${plaque[5]}")
        section.add("${plaque[3]}")
        section.add("${plaque[0]}${plaque[1]}${plaque[2]}")
        section.add("${plaque[6]}${plaque[7]}")
        return section
    }

    fun finallPrice(fouls: List<Foul>): String {
        var price = 0
        for (i in fouls.indices) {
            price += fouls[i].amount
        }
        return priceFormat(price)
    }

}