package ir.rezarasoulzadeh.pelakomak.view.activity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import ir.rezarasoulzadeh.pelakomak.service.utils.StateMap
import kotlinx.android.synthetic.main.activity_for_motorcycle.*
import kotlinx.android.synthetic.main.dialog_for_info_car.view.*

class MotorcycleActivity : AppCompatActivity() {

    lateinit var stateName: String
    private lateinit var parentView: View
    private val snackbar = CustomSnackbar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_motorcycle)

        val motorcycleStateNumber = findViewById<EditText>(R.id.motorcycle_state_number)
        val motorcycleOtherNumber = findViewById<EditText>(R.id.motorcycle_other_number)
        val layout = findViewById<LinearLayout>(R.id.motorcycleActivityParentView)
        parentView = findViewById<LinearLayout>(R.id.motorcycleActivityParentView)

        motorcycleStateNumber.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                motorcycleStateNumber.text.clear()
            }
        }

        motorcycleOtherNumber.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                motorcycleOtherNumber.text.clear()
            }
        }

        motorcycleStateNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // when the number box has be completed
                if (motorcycleStateNumber.text.length == 3) {
                    motorcycleOtherNumber.requestFocus()
                }
            }
        })

        motorcycleOtherNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // when the number box has be completed
                if (motorcycleOtherNumber.text.length == 5) {
                    search(motorcycleStateNumber)
                    // point to the irrelevant location to prevent from clear it
                    layout.requestFocus()
                }
            }
        })

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        infoButton.setOnClickListener {
            val infoView =
                LayoutInflater.from(this).inflate(R.layout.dialog_for_info_motorcycle, null)

            val infoViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(infoView) }

            val infoAlertDialog = infoViewBuilder.show()

            infoAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            infoAlertDialog.setCanceledOnTouchOutside(false)

            infoView.infoCloseButton.setOnClickListener {
                infoAlertDialog.dismiss()
            }
        }

    }

    private fun search(stateNumber: EditText) {
        hideKeyboard(this)

        // if dangerous (0 is not valid)
        if (stateNumber.text.toString().contains("0")) {

            snackbar.show(
                "پلاک نامعتبر می باشد",
                "long",
                parentView,
                this.layoutInflater
            )

            // enable the device vibration
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        50,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(50)
            }
            motorcycleStateImageView.setImageResource(StateMap().getState(" "))
        } else {

            // parsing the number box's number to Int
            val number = stateNumber.text.toString().toInt()
            when (number) {
                in 391..397 -> stateName = "آذربایجان شرقی"
                in 371..376 -> stateName = "آذربایجان غربی"
                in 442..443 -> stateName = "اردبیل"
                in 618..634 -> stateName = "اصفهان"
                in 319..324 -> stateName = "البرز"
                547 -> stateName = "ایلام"
                in 827..829 -> stateName = "بوشهر"
                811 -> stateName = "بوشهر"
                in 111..137 -> stateName = "تهران"
                555 -> stateName = "چهارمهال و بختیاری"
                in 779..781 -> stateName = "خراسان شمالی"
                in 761..776 -> stateName = "خراسان رضوی"
                in 791..792 -> stateName = "خراسان جنوبی"
                in 561..569 -> stateName = "خوزستان"
                in 479..482 -> stateName = "زنجان"
                in 751..753 -> stateName = "سمنان"
                in 819..823 -> stateName = "سیستان و بلوچستان"
                in 687..697 -> stateName = "فارس"
                in 523..525 -> stateName = "قزوین"
                in 611..614 -> stateName = "قم"
                in 461..462 -> stateName = "کردستان"
                in 812..817 -> stateName = "کرمان"
                in 514..517 -> stateName = "کرمانشاه"
                571 -> stateName = "کهکیلویه و بویر احمد"
                in 596..597 -> stateName = "گلستان"
                in 578..582 -> stateName = "گیلان"
                in 538..543 -> stateName = "لرستان"
                in 586..588 -> stateName = "مازندران"
                in 531..536 -> stateName = "مرکزی"
                in 835..839 -> stateName = "هرمزگان"
                in 498..499 -> stateName = "همدان"
                511 -> stateName = "همدان"
                in 637..642 -> stateName = "یزد"
                else -> {

                    snackbar.show(
                        "پلاک نامعتبر می باشد",
                        "long",
                        parentView,
                        this.layoutInflater
                    )

                    stateName = "پلاک نامعتبر است"

                    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        vibrator.vibrate(50)
                    }
                }
            }
            motorcycleStateImageView.setImageResource(StateMap().getState(stateName))

        }

    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}