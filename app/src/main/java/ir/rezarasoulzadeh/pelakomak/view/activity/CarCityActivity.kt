package ir.rezarasoulzadeh.pelakomak.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import kotlinx.android.synthetic.main.activity_for_car_city.*

class CarCityActivity : AppCompatActivity() {

    private val snackbar = CustomSnackbar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_car_city)

        backButton.setOnClickListener {
            super.onBackPressed()
        }

    }

}