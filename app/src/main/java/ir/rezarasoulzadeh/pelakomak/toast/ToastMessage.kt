package ir.rezarasoulzadeh.pelakomak.toast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import ir.rezarasoulzadeh.pelakomak.R
import kotlinx.android.synthetic.main.toast_message_for_inform.view.*

class ToastMessage {

    fun show(message: String, type: String, duration: String, context: Context, inflater: LayoutInflater) {

        val customToastView: View

        if (type == "inform") {
            customToastView = inflater.inflate(R.layout.toast_message_for_inform, null)
        } else {
            customToastView = inflater.inflate(R.layout.toast_message_for_warning, null)
        }

        val customToast = Toast(context)

        customToast.setGravity(Gravity.TOP, 0, 5)
        customToast.view = customToastView
        customToastView.inform_message.text = message

        if (duration == "long")
            customToast.duration = Toast.LENGTH_LONG
        if (duration == "short")
            customToast.duration = Toast.LENGTH_SHORT

        customToast.show()
    }
}