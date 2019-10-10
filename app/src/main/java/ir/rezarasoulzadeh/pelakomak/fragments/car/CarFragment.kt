package ir.rezarasoulzadeh.pelakomak.fragments.car

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.toast.ToastMessage

class CarFragment : Fragment() {

    private val toast = ToastMessage()

    private val validCharacters =
        arrayListOf("ب", "ج", "د", "س", "ص", "ط", "ق", "ل", "م", "ن", "و", "ه", "ی")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_for_car, container, false)

        val carStateNumber = root.findViewById<EditText>(R.id.car_state_number)
        val carCountyCharacter = root.findViewById<EditText>(R.id.car_county_character)
        val carState = root.findViewById<TextView>(R.id.car_state)
        val carCounty = root.findViewById<TextView>(R.id.car_county)
        val layout = root.findViewById<ConstraintLayout>(R.id.layout)


        carStateNumber.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                carStateNumber.text.clear()
            }
        }

        carCountyCharacter.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (carStateNumber.text.isEmpty()) {
                    toast.show(
                        "ابتدا دو رقم سمت راست را وارد کنید",
                        "warning",
                        "long",
                        this.activity!!,
                        inflater
                    )
                    layout.requestFocus()
                } else {
                    carCountyCharacter.text.clear()
                }
            }
        }

        // auto cross pointer to character box when two digit number has been entered
        carStateNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carStateNumber.text.length == 2) {
                    carCountyCharacter.requestFocus()
                }
            }
        })

        // auto search when the one character has been entered
        carCountyCharacter.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carCountyCharacter.text.length == 1) {
                    // call search for find the suitable object in database
                    search(carStateNumber, carState, carCountyCharacter, carCounty)

                    // change pointer to irrelevant location to prevent from clean the number box
                    layout.requestFocus()
                }
            }
        })

        return root
    }

    @SuppressLint("Recycle")
    fun search(
        stateNumber: EditText,
        stateName: TextView,
        countyCharacter: EditText,
        countyName: TextView
    ) {
        // easy work (hide key board)
        if (stateNumber.length() == 2) {
            hideKeyboard(this.activity!!)
        }

        // easy work (hide key board)
        if (countyCharacter.length() == 1) {
            hideKeyboard(this.activity!!)
        }

        // var to save database response
        val dbResponse: String

        ////////////////////////////////////////////////////////////////////////////////////////////
        // give data base response
        dbResponse = "قزوین-قزوین، شهرستان البرز"
        ////////////////////////////////////////////////////////////////////////////////////////////

        // invalid response from database or invalid characters
        if (dbResponse == "" || countyCharacter.text.toString() !in validCharacters) {
            stateName.text = "پلاک نامعتبر است"
            countyName.text = "پلاک نامعتبر است"
            stateName.setTextColor(Color.RED)
            countyName.setTextColor(Color.RED)

            // enable the vibration of device
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
            // split true response
            val responseSections = dbResponse.split("-".toRegex())
            stateName.text = responseSections[0]
            countyName.text = responseSections[1]
            countyName.isSelected = true
            stateName.setTextColor(Color.BLACK)
            countyName.setTextColor(Color.BLACK)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}