package ir.rezarasoulzadeh.pelakomak.view.activity

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Car
import ir.rezarasoulzadeh.pelakomak.service.database.CarNumberplateDB
import ir.rezarasoulzadeh.pelakomak.view.adapter.CarAdapter
import kotlinx.android.synthetic.main.activity_for_car_city.*
import kotlinx.android.synthetic.main.dialog_for_news.view.*
import kotlinx.android.synthetic.main.dialog_for_no_location.view.*


class CarCityActivity : AppCompatActivity() {

    private lateinit var carDatabase: CarNumberplateDB
    private lateinit var carRecyclerView: RecyclerView
    private lateinit var carEmptyView: TextView
    private lateinit var adapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_car_city)

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        carDatabase = CarNumberplateDB(this)

        carRecyclerView = findViewById(R.id.carCityRecyclerView)
        carEmptyView = findViewById(R.id.carCityEmptyView)

        var response: ArrayList<Car>

        cityEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                cityEditText.text.clear()
            }
        }

        searchButton.setOnClickListener {
            response = carDatabase.getCity(cityEditText.text.toString())
            if (response.isEmpty()) {
                hideKeyboard(this)

                val noLocationView = LayoutInflater.from(this).inflate(R.layout.dialog_for_no_location, null)

                val noLocationViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(noLocationView) }

                val noLocationAlertDialog = noLocationViewBuilder.show()

                noLocationAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                noLocationAlertDialog.setCanceledOnTouchOutside(false)

                noLocationView.noLocationCloseButton.setOnClickListener {
                    noLocationAlertDialog.dismiss()
                }
            } else {
                hideKeyboard(this)
                adapter = CarAdapter(response)
                cityEditText.clearFocus()
                carRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                carRecyclerView.visibility = View.VISIBLE
                carEmptyView.visibility = View.GONE
            }
        }

        cityEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                response = carDatabase.getCity(cityEditText.text.toString())
                if (response.isEmpty()) {
                    hideKeyboard(this)

                    val noLocationView = LayoutInflater.from(this).inflate(R.layout.dialog_for_no_location, null)

                    val noLocationViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(noLocationView) }

                    val noLocationAlertDialog = noLocationViewBuilder.show()

                    noLocationAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    noLocationAlertDialog.setCanceledOnTouchOutside(false)

                    noLocationView.noLocationCloseButton.setOnClickListener {
                        noLocationAlertDialog.dismiss()
                    }
                } else {
                    hideKeyboard(this)
                    adapter = CarAdapter(response)
                    cityEditText.clearFocus()
                    carRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    carRecyclerView.visibility = View.VISIBLE
                    carEmptyView.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })

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