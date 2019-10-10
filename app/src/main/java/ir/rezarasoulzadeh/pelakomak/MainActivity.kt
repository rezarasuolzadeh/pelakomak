package ir.rezarasoulzadeh.pelakomak

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ir.rezarasoulzadeh.pelakomak.toast.ToastMessage

class MainActivity : AppCompatActivity() {

    private val toast = ToastMessage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // hide the action bar
        supportActionBar!!.hide()

        // full screen mode
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        val context = this
        val inflater = this.layoutInflater

        if (doubleBackToExitPressedOnce) finishAffinity()

        this.doubleBackToExitPressedOnce = true

        toast.show("برای خروج دوباره دکمه بازگشت را لمس کنید", "inform", "short", context, inflater)

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }

}

