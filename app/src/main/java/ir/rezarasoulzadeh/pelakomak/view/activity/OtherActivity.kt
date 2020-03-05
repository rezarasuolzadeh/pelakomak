package ir.rezarasoulzadeh.pelakomak.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Other
import ir.rezarasoulzadeh.pelakomak.view.adapter.OtherAdapter
import kotlinx.android.synthetic.main.activity_for_other.*

class OtherActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_other)

        val otherRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_other)

        otherRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val other = arrayOf(
            Other("ارتش", R.drawable.artesh),
            Other("سیاسی", R.drawable.siasi),
            Other("سپاه", R.drawable.sepah),
            Other("دولتی", R.drawable.dolati),
            Other(
                "وزارت دفاع",
                R.drawable.vezaratdefae
            ),
            Other("خدمت", R.drawable.khedmat),
            Other(
                "کشاورزی",
                R.drawable.keshavarzi
            ),
            Other(
                "معلولین",
                R.drawable.maloolin
            ),
            Other("تاکسی", R.drawable.taxi),
            Other(
                "نیروهای مسلّح",
                R.drawable.nirohayemosallah
            ),
            Other(
                "گذر موقت",
                R.drawable.gozarmovaghat
            ),
            Other("پلیس", R.drawable.police),
            Other("عمومی", R.drawable.omoomi),
            Other(
                "تشریفات",
                R.drawable.tashrifat
            ),
            Other(
                "تاریخی",
                R.drawable.tarikhi
            )
        )

        val adapter =
            OtherAdapter(other)
        otherRecyclerView.adapter = adapter

        backButton.setOnClickListener {
            super.onBackPressed()
        }

    }

}