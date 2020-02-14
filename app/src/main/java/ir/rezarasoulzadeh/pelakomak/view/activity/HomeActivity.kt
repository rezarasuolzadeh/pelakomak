package ir.rezarasoulzadeh.pelakomak.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.service.utils.Seen
import ir.rezarasoulzadeh.pelakomak.view.fragment.CarFragment
import ir.rezarasoulzadeh.pelakomak.view.fragment.FreezoneFragment
import ir.rezarasoulzadeh.pelakomak.view.fragment.HomeFragment
import ir.rezarasoulzadeh.pelakomak.view.fragment.MotorcycleFragment
import ir.rezarasoulzadeh.pelakomak.view.fragment.OtherFragment
import ir.rezarasoulzadeh.pelakomak.service.utils.Snackbar
import kotlinx.android.synthetic.main.dialog_for_news.view.*

class HomeActivity : AppCompatActivity() {

    private val snackbar = Snackbar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // hide the action bar
        supportActionBar!!.hide()

        val seen = Seen(this)

        if (seen.isFirstSeen() == "yes") {
            seen.setFirstSeen()

            Handler().postDelayed({
                val newsView = LayoutInflater.from(this).inflate(R.layout.dialog_for_news, null)

                val newsViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(newsView) }

                val newsAlertDialog = newsViewBuilder.show()

                newsAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                newsAlertDialog.setCanceledOnTouchOutside(false)

                newsView.newsCloseButton.setOnClickListener {
                    newsAlertDialog.dismiss()
                }
            }, 1500)
        }

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_car,
                R.id.navigation_motorcycle,
                R.id.navigation_other,
                R.id.navigation_freezone
            )
        )

        val navigationCar = findViewById<BottomNavigationItemView>(R.id.navigation_car)
        val navigationMotorcycle =
            findViewById<BottomNavigationItemView>(R.id.navigation_motorcycle)
        val navigationOther = findViewById<BottomNavigationItemView>(R.id.navigation_other)
        val navigationFreezone = findViewById<BottomNavigationItemView>(R.id.navigation_freezone)
        val navigationHome = findViewById<BottomNavigationItemView>(R.id.navigation_home)

        navigationCar.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = CarFragment()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navView.menu.findItem(R.id.navigation_car).isChecked = true
        }

        navigationMotorcycle.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = MotorcycleFragment()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navView.menu.findItem(R.id.navigation_motorcycle).isChecked = true
        }

        navigationOther.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = OtherFragment()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navView.menu.findItem(R.id.navigation_other).isChecked = true
        }

        navigationFreezone.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = FreezoneFragment()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navView.menu.findItem(R.id.navigation_freezone).isChecked = true
        }

        navigationHome.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = HomeFragment()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navView.menu.findItem(R.id.navigation_home).isChecked = true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        val view = this.window.decorView
        val inflater = this.layoutInflater

        if (doubleBackToExitPressedOnce) finishAffinity()

        this.doubleBackToExitPressedOnce = true

        snackbar.show("برای خروج دوباره دکمه بازگشت را لمس کنید", "short", view, inflater)

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}