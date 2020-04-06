package ir.rezarasoulzadeh.pelakomak.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Motorcycle
import ir.rezarasoulzadeh.pelakomak.service.database.MotorcycleDatabase
import ir.rezarasoulzadeh.pelakomak.view.adapter.MotorcycleAdapter
import kotlinx.android.synthetic.main.activity_for_motorcycle_city.*


class MotorcycleCityActivity : AppCompatActivity() {

    private lateinit var motorcycleDatabase: MotorcycleDatabase
    private lateinit var motorcycleRecyclerView: RecyclerView
    private lateinit var adapter: MotorcycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_motorcycle_city)

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        motorcycleDatabase = MotorcycleDatabase(this)

        motorcycleRecyclerView = findViewById(R.id.motorcycleCityRecyclerView)

        var response: ArrayList<Motorcycle>

        cityEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                cityEditText.text.clear()
            }
        }

        searchButton.setOnClickListener {
            response = motorcycleDatabase.getCity(cityEditText.text.toString())
            if(response.isEmpty()){
                Toast.makeText(this, "نتیجه ای یافت نشد", Toast.LENGTH_LONG).show()
            } else {
                hideKeyboard(this)
                adapter = MotorcycleAdapter(response)
                cityEditText.clearFocus()
                motorcycleRecyclerView.adapter = adapter
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