package ir.rezarasoulzadeh.pelakomak.view.activity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ir.rezarasoulzadeh.pelakomak.R
import kotlinx.android.synthetic.main.activity_for_motorcycle.*

class MotorcycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_motorcycle)

        val motorcycleStateNumber = findViewById<EditText>(R.id.motorcycle_state_number)
        val motorcycleState = findViewById<TextView>(R.id.motorcycle_state)
        val layout = findViewById<LinearLayout>(R.id.motorcycleActivityParentView)

        motorcycleStateNumber.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                motorcycleStateNumber.text.clear()
            }
        }

        motorcycleStateNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // when the number box has be completed
                if (motorcycleStateNumber.text.length == 3) {
                    search(motorcycleStateNumber, motorcycleState)

                    // point to the irrelevant location to prevent from clear it
                    layout.requestFocus()
                }
            }
        })

        backButton.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun search(stateNumber: EditText, stateName: TextView) {
        // easy working (hide key pad)
        if (stateNumber.length() == 3) {
            hideKeyboard(this)
        }

        // if dangerous (0 is not valid)
        if (stateNumber.text.toString().contains("0")) {
            stateName.text = "پلاک نامعتبر است"
            stateName.setTextColor(Color.RED)

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
        } else {
            // find the state name in below switch case with number range
            stateName.setTextColor(Color.WHITE)

            // parsing the number box's number to Int
            val number = stateNumber.text.toString().toInt()
            when (number) {
                in 391..397 -> stateName.text = "آذربایجان شرقی"
                in 371..376 -> stateName.text = "آذربایجان غربی"
                in 442..443 -> stateName.text = "اردبیل"
                in 618..634 -> stateName.text = "اصفهان"
                in 319..324 -> stateName.text = "البرز"
                547 -> stateName.text = "ایلام"
                in 827..829 -> stateName.text = "بوشهر"
                811 -> stateName.text = "بوشهر"
                in 111..137 -> stateName.text = "تهران"
                555 -> stateName.text = "چهارمهال و بختیاری"
                in 779..781 -> stateName.text = "خراسان شمالی"
                in 761..776 -> stateName.text = "خراسان رضوی"
                in 791..792 -> stateName.text = "خراسان جنوبی"
                in 561..569 -> stateName.text = "خوزستان"
                in 479..482 -> stateName.text = "زنجان"
                in 751..753 -> stateName.text = "سمنان"
                in 819..823 -> stateName.text = "سیستان و بلوچستان"
                in 687..697 -> stateName.text = "فارس"
                in 523..525 -> stateName.text = "قزوین"
                in 611..614 -> stateName.text = "قم"
                in 461..462 -> stateName.text = "کردستان"
                in 812..817 -> stateName.text = "کرمان"
                in 514..517 -> stateName.text = "کرمانشاه"
                571 -> stateName.text = "کهکیلویه و بویر احمد"
                in 596..597 -> stateName.text = "گلستان"
                in 578..582 -> stateName.text = "گیلان"
                in 538..543 -> stateName.text = "لرستان"
                in 586..588 -> stateName.text = "مازندران"
                in 531..536 -> stateName.text = "مرکزی"
                in 835..839 -> stateName.text = "هرمزگان"
                in 498..499 -> stateName.text = "همدان"
                511 -> stateName.text = "همدان"
                in 637..642 -> stateName.text = "یزد"
                else -> {
                    stateName.text = "پلاک نامعتبر است"
                    stateName.setTextColor(Color.RED)
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