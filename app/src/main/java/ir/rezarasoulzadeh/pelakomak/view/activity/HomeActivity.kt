package ir.rezarasoulzadeh.pelakomak.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Menu
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import ir.rezarasoulzadeh.pelakomak.service.utils.Seen
import kotlinx.android.synthetic.main.content_for_home.*
import kotlinx.android.synthetic.main.content_for_home.view.*
import kotlinx.android.synthetic.main.dialog_for_news.view.*
import kotlinx.android.synthetic.main.activity_for_home.*
import java.lang.Exception
import java.util.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val snackbar = CustomSnackbar()

    private lateinit var parentView: View
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuList: ArrayList<Menu>
    private lateinit var adapter: ir.rezarasoulzadeh.pelakomak.view.adapter.MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(Gravity.RIGHT)
        }

        menuRecyclerView = findViewById(R.id.menuRecyclerView)
        menuList = ArrayList()

        menuList.add(Menu("پلاک اتومبیل", 1, R.drawable.ic_car))
        menuList.add(Menu("پلاک موتورسیکلت", 2, R.drawable.ic_motorcycle))
        menuList.add(Menu("سایر پلاک ها", 3, R.drawable.ic_other))
        menuList.add(Menu("پلاک مناطق آزاد", 4, R.drawable.ic_freezone))
        menuList.add(Menu("استان اتومبیل", 5, R.drawable.ic_map))
        menuList.add(Menu("استان موتورسیکلت", 6, R.drawable.ic_map))

        adapter = ir.rezarasoulzadeh.pelakomak.view.adapter.MenuAdapter(menuList)

        menuRecyclerView.adapter = adapter

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

    }

    @SuppressLint("StringFormatInvalid")
    private fun fireBaseChannelId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("tag", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result?.token

                val msg = getString(R.string.messageTokenFormat, token)
                Log.d("tag", msg)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

            })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val inflater = this.layoutInflater

        when (item.itemId) {
            R.id.menuMailItem -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.type = "text/plain"
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("reza.rassoulzadeh@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "پلاکمک")
                startActivity(Intent.createChooser(intent, null))
                drawerLayout.closeDrawer(Gravity.RIGHT)
            }
            R.id.menuStarItem -> {
                try {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.data = Uri.parse(this.resources.getString(R.string.bazaarStarLink))
                    intent.setPackage("com.farsitel.bazaar")
                    startActivity(intent)
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                } catch (e: Exception) {
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                    snackbar.show("ابتدا برنامه بازار رو نصب کنید", "short", parentView, inflater)
                }

            }
            R.id.menuShareItem -> {
                try {
                    val productShareBody = "پلاکمک\nپلاک یاب حرفه ای برای هر وسیله نقلیه\nلینک نصب:\n" + this.resources.getString(R.string.bazaarShareLink)
                    val productShareIntent = Intent(Intent.ACTION_SEND)
                    productShareIntent.type = "text/plain"
                    productShareIntent.putExtra(Intent.EXTRA_SUBJECT, "پلاکمک")
                    productShareIntent.putExtra(Intent.EXTRA_TEXT, productShareBody)
                    startActivity(Intent.createChooser(productShareIntent, "معرفی به دوستان"))
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                } catch (e: Exception) {
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                }
            }
            R.id.developerItem -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(this.resources.getString(R.string.bazaarDeveloperLink))
                    intent.setPackage(this.resources.getString(R.string.bazaarPackage))
                    startActivity(intent)
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                } catch (e: Exception) {
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                    snackbar.show("ابتدا برنامه بازار رو نصب کنید", "short", parentView, inflater)
                }
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