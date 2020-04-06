package ir.rezarasoulzadeh.pelakomak.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Car
import ir.rezarasoulzadeh.pelakomak.service.database.CarNumberplateDB
import ir.rezarasoulzadeh.pelakomak.view.adapter.CarAdapter
import kotlinx.android.synthetic.main.activity_for_car_city.*


class CarCityActivity : AppCompatActivity() {

    private lateinit var carDatabase: CarNumberplateDB
    private lateinit var carRecyclerView: RecyclerView
    private lateinit var adapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_car_city)

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        carDatabase = CarNumberplateDB(this)

        carRecyclerView = findViewById(R.id.carCityRecyclerView)

        var response: ArrayList<Car>

        cityEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                cityEditText.text.clear()
            }
        }

        searchButton.setOnClickListener {
            response = carDatabase.getCity(cityEditText.text.toString())
            if (response.isEmpty()) {
                Toast.makeText(this, "نتیجه ای یافت نشد", Toast.LENGTH_LONG).show()
            } else {
                hideKeyboard(this)
                adapter = CarAdapter(response)
                cityEditText.clearFocus()
                carRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
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