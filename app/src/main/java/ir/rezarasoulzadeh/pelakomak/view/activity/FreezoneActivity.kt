package ir.rezarasoulzadeh.pelakomak.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Freezone
import ir.rezarasoulzadeh.pelakomak.view.adapter.FreezoneAdapter
import kotlinx.android.synthetic.main.activity_for_freezone.*

class FreezoneActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_freezone)

        val freezoneRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_freezone)
        freezoneRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val freezone = arrayOf(
            Freezone("کیش", R.drawable.kish),
            Freezone("قشم", R.drawable.gheshm),
            Freezone("چابهار", R.drawable.chabahar),
            Freezone("ارس", R.drawable.aras),
            Freezone("اروند", R.drawable.arvand),
            Freezone("انزلی", R.drawable.anzali),
            Freezone("ماکو", R.drawable.maku)
        )

        val adapter = FreezoneAdapter(freezone)
        freezoneRecyclerView.adapter = adapter

        backButton.setOnClickListener {
            super.onBackPressed()
        }
    }

}