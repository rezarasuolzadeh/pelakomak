package ir.rezarasoulzadeh.pelakomak.service.utils

import android.content.Context
import android.content.SharedPreferences

class Seen(context: Context) {

    private var seen: String = "seen"
    private var seenSharedPreference: SharedPreferences

    init {
        val userPreference = "seenSharedPreference"
        seenSharedPreference = context.getSharedPreferences(userPreference, Context.MODE_PRIVATE)
    }

    fun setFirstSeen() {
        val editor: SharedPreferences.Editor = seenSharedPreference.edit()
        editor.putString(seen, "no")
        editor.apply()
    }

    fun isFirstSeen() : String? {
        return seenSharedPreference.getString(seen, "yes")
    }

}