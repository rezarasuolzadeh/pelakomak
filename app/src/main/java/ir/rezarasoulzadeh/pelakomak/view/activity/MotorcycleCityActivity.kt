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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Motorcycle
import ir.rezarasoulzadeh.pelakomak.service.database.MotorcycleDatabase
import ir.rezarasoulzadeh.pelakomak.view.adapter.MotorcycleAdapter
import kotlinx.android.synthetic.main.activity_for_motorcycle_city.*
import kotlinx.android.synthetic.main.dialog_for_no_location.view.*


class MotorcycleCityActivity : AppCompatActivity() {

    private lateinit var motorcycleDatabase: MotorcycleDatabase
    private lateinit var motorcycleRecyclerView: RecyclerView
    private lateinit var motorcycleEmptyView: TextView
    private lateinit var adapter: MotorcycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_motorcycle_city)

        backButton.setOnClickListener {
            super.onBackPressed()
        }

        motorcycleDatabase = MotorcycleDatabase(this)

        motorcycleRecyclerView = findViewById(R.id.motorcycleCityRecyclerView)
        motorcycleEmptyView = findViewById(R.id.motorcycleCityEmptyView)

        var response: ArrayList<Motorcycle>

        cityEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                cityEditText.text.clear()
            }
        }

        searchButton.setOnClickListener {
            response = motorcycleDatabase.getCity(cityEditText.text.toString())
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
                adapter = MotorcycleAdapter(response)
                cityEditText.clearFocus()
                motorcycleRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                motorcycleRecyclerView.visibility = View.VISIBLE
                motorcycleEmptyView.visibility = View.GONE
            }
        }

        cityEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                response = motorcycleDatabase.getCity(cityEditText.text.toString())
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
                    adapter = MotorcycleAdapter(response)
                    cityEditText.clearFocus()
                    motorcycleRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    motorcycleRecyclerView.visibility = View.VISIBLE
                    motorcycleEmptyView.visibility = View.GONE
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