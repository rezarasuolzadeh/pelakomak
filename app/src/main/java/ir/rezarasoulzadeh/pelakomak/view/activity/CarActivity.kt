package ir.rezarasoulzadeh.pelakomak.view.activity

import android.annotation.SuppressLint
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
import ir.rezarasoulzadeh.pelakomak.service.database.CarNumberplateDB
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import ir.rezarasoulzadeh.pelakomak.service.utils.StateMap
import kotlinx.android.synthetic.main.activity_for_car.*
import kotlinx.android.synthetic.main.dialog_for_info_car.view.*

class CarActivity : AppCompatActivity() {

    private val snackbar = CustomSnackbar()
    private lateinit var parentView: View

    private val validCharacters =
        arrayListOf("ب", "ج", "د", "س", "ص", "ط", "ق", "ل", "م", "ن", "و", "ه", "ی")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_car)

        val inflater = this.layoutInflater

        val carStateNumber = findViewById<EditText>(R.id.car_state_number)
        val carCountyCharacter = findViewById<EditText>(R.id.car_county_character)
        val carFirstCharacter = findViewById<EditText>(R.id.car_first_character)
        val carThirdCharacter = findViewById<EditText>(R.id.car_third_character)
        val layout = findViewById<LinearLayout>(R.id.carActivityParentView)
        parentView = findViewById<LinearLayout>(R.id.carActivityParentView)

        carStateNumber.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                carStateNumber.text.clear()
            }
        }

        carCountyCharacter.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                carCountyCharacter.text.clear()
            }
        }

        carFirstCharacter.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                carFirstCharacter.text.clear()
            }
        }

        carThirdCharacter.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                carThirdCharacter.text.clear()
            }
        }

        carFirstCharacter.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carFirstCharacter.text.length == 2) {
                    carCountyCharacter.requestFocus()
                }
            }
        })

        carThirdCharacter.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carThirdCharacter.text.length == 3) {
                    carStateNumber.requestFocus()
                }
            }
        })

        carStateNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carStateNumber.text.length == 2) {
                    search(carStateNumber, carCountyCharacter)
                    layout.requestFocus()
                }
            }
        })

        carCountyCharacter.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (carCountyCharacter.text.length == 1) {
                    carThirdCharacter.requestFocus()
                }
            }
        })

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        infoButton.setOnClickListener {
            val infoView = LayoutInflater.from(this).inflate(R.layout.dialog_for_info_car, null)

            val infoViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(infoView) }

            val infoAlertDialog = infoViewBuilder.show()

            infoAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            infoAlertDialog.setCanceledOnTouchOutside(false)

            infoView.infoCloseButton.setOnClickListener {
                infoAlertDialog.dismiss()
            }
        }

    }

    @SuppressLint("Recycle")
    fun search(
        stateNumber: EditText,
        countyCharacter: EditText
    ) {
        // easy work (hide key board)
        if (stateNumber.length() == 2) {
            hideKeyboard(this)
        }

        // easy work (hide key board)
        if (countyCharacter.length() == 1) {
            hideKeyboard(this)
        }

        // var to save database response
        val dbResponse: String

        /////////////////////////////////// GIVE DATABASE RESPONSE /////////////////////////////////
        val carDB = CarNumberplateDB(this)
        dbResponse = carDB.get(stateNumber.text.toString(), countyCharacter.text.toString())
        ////////////////////////////////////////////////////////////////////////////////////////////

        // invalid response from database or invalid characters
        if (dbResponse == "" || countyCharacter.text.toString() !in validCharacters) {
            carStateImageView.setImageResource(StateMap().getState(" "))

            snackbar.show(
                "پلاک نامعتبر می باشد",
                "long",
                parentView,
                this.layoutInflater
            )

            // enable the vibration of device
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
            // split true response
            val responseSections = dbResponse.split("-".toRegex())
            carStateImageView.setImageResource(StateMap().getState(responseSections[0]))
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