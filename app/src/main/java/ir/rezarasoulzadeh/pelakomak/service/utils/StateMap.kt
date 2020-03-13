package ir.rezarasoulzadeh.pelakomak.service.utils

import ir.rezarasoulzadeh.pelakomak.R

class StateMap {

    fun getState(state: String): Int {
        return when (state) {
            "آذربایجان شرقی" -> R.drawable.state_azarbaijan_sharghi
            "آذربایجان غربی" -> R.drawable.state_azarbaijan_gharbi
            "اردبیل" -> R.drawable.state_ardebil
            "اصفهان" -> R.drawable.state_esfahan
            "البرز" -> R.drawable.state_alborz
            "ایلام" -> R.drawable.state_ilam
            "بوشهر" -> R.drawable.state_booshehr
            "تهران" -> R.drawable.state_tehran
            "چهارمحال و بختیاری" -> R.drawable.state_charmahal
            "خراسان شمالی" -> R.drawable.state_khorasan_shomali
            "خراسان رضوی" -> R.drawable.state_khorasan_razavi
            "خراسان جنوبی" -> R.drawable.state_khorasan_jonoobi
            "خوزستان" -> R.drawable.state_khoozestan
            "زنجان" -> R.drawable.state_zanjan
            "سمنان" -> R.drawable.state_semnan
            "سیستان و بلوچستان" -> R.drawable.state_sistan
            "فارس" -> R.drawable.state_fars
            "قزوین" -> R.drawable.state_qazvin
            "قم" -> R.drawable.state_qom
            "کردستان" -> R.drawable.state_kordestan
            "کرمان" -> R.drawable.state_kerman
            "کرمانشاه" -> R.drawable.state_kermanshah
            "کهکیلویه و بویر احمد" -> R.drawable.state_kohkilooyeh
            "گلستان" -> R.drawable.state_golestan
            "گیلان" -> R.drawable.state_gilan
            "لرستان" -> R.drawable.state_lorestan
            "مازندران" -> R.drawable.state_mazandaran
            "مرکزی" -> R.drawable.state_markazi
            "هرمزگان" -> R.drawable.state_hormozgan
            "همدان" -> R.drawable.state_hamedan
            "یزد" -> R.drawable.state_yazd
            else -> R.drawable.state_null
        }
    }

}