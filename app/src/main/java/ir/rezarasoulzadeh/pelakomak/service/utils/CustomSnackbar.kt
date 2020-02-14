package ir.rezarasoulzadeh.pelakomak.service.utils

import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import ir.rezarasoulzadeh.pelakomak.R
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar {

    fun show(message: String, duration: String, view: View, inflater: LayoutInflater) {

        val snackbarView: View = inflater.inflate(R.layout.snackbar_for_message, null)

        val snackbarText = snackbarView.findViewById(R.id.snackbar_message) as TextView

        val snackbar: Snackbar

        if (duration == "short")
            snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        else
            snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)

        val layout = snackbar.view as Snackbar.SnackbarLayout

        val textView = layout.findViewById(R.id.snackbar_text) as TextView
        textView.visibility = View.INVISIBLE

        snackbarText.text = message

        layout.setPadding(0, 0, 0, 0)

        layout.addView(snackbarView, 0)

        snackbar.show()
    }
}