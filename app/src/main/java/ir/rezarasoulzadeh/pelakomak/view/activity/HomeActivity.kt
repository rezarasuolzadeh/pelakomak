package ir.rezarasoulzadeh.pelakomak.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import ir.rezarasoulzadeh.pelakomak.service.utils.Seen
import kotlinx.android.synthetic.main.content_for_home.*
import kotlinx.android.synthetic.main.content_for_home.view.*
import kotlinx.android.synthetic.main.dialog_for_news.view.*
import kotlinx.android.synthetic.main.activity_for_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val snackbar = CustomSnackbar()

    private lateinit var parentView : View

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        navView.setNavigationItemSelectedListener(this)

        parentView = findViewById(R.id.drawer_layout)

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

        contentHomeLayout.newsButton.setOnClickListener {
            val newsView = LayoutInflater.from(this).inflate(R.layout.dialog_for_news, null)

            val newsViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(newsView) }

            val newsAlertDialog = newsViewBuilder.show()

            newsAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            newsAlertDialog.setCanceledOnTouchOutside(false)

            newsView.newsCloseButton.setOnClickListener {
                newsAlertDialog.dismiss()
            }
        }

        contentHomeLayout.foulButtonLayout.setOnClickListener {
            val intent = Intent(this, FoulActivity::class.java)
            startActivity(intent)
        }

        contentHomeLayout.carButtonLayout.setOnClickListener {
            val intent = Intent(this, CarActivity::class.java)
            startActivity(intent)
        }


        contentHomeLayout.motorcycleButtonLayout.setOnClickListener {
            val intent = Intent(this, MotorcycleActivity::class.java)
            startActivity(intent)
        }

        contentHomeLayout.otherButtonLayout.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
        }

        contentHomeLayout.freezoneButtonLayout.setOnClickListener {
            val intent = Intent(this, FreezoneActivity::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("StringFormatInvalid")
    private fun fireBaseChannelId(){
        // find the token of notification chanel id
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("tag", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d("tag", msg)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

            })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuMailItem -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.type = "text/plain"
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("reza.rassoulzadeh@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "پلاکمک")
                startActivity(Intent.createChooser(intent, "ارسال پیام از طریق:"))
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            R.id.menuStarItem -> {
                val intent = Intent(Intent.ACTION_EDIT)
                intent.data = Uri.parse("bazaar://details?id=" + this.resources.getString(R.string.bazaarPackage))
                intent.setPackage("com.farsitel.bazaar")
                startActivity(intent)
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            R.id.menuShareItem -> {
                val productShareBody = "پلاکمک\nپلاک یاب حرفه ای برای هر وسیله نقلیه\nلینک نصب:\n" + this.resources.getString(R.string.bazaarLink)
                val productShareIntent = Intent(Intent.ACTION_SEND)
                productShareIntent.type = "text/plain"
                productShareIntent.putExtra(Intent.EXTRA_SUBJECT, "پلاکمک")
                productShareIntent.putExtra(Intent.EXTRA_TEXT, productShareBody)
                startActivity(Intent.createChooser(productShareIntent, "معرفی به دوستان"))
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            R.id.developerItem -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("bazaar://collection?slug=by_author&aid=" + this.resources.getString(R.string.bazaarId))
                intent.setPackage("com.farsitel.bazaar")
                startActivity(intent)
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }
        return true
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        val inflater = this.layoutInflater

        if (doubleBackToExitPressedOnce) finishAffinity()

        this.doubleBackToExitPressedOnce = true

        snackbar.show("برای خروج دوباره دکمه بازگشت را لمس کنید", "short", parentView, inflater)

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}