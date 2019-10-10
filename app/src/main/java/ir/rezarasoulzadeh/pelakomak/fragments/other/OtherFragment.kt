package ir.rezarasoulzadeh.pelakomak.fragments.other

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.list.other.Other
import ir.rezarasoulzadeh.pelakomak.list.other.OtherAdapter

class OtherFragment : Fragment() {

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_for_other, container, false)

        val otherRecyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_other)
        otherRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayout.VERTICAL, false)

        val other = arrayOf(
            Other("ارتش", R.drawable.artesh),
            Other("سیاسی", R.drawable.siasi),
            Other("سپاه", R.drawable.sepah),
            Other("دولتی", R.drawable.dolati),
            Other("وزارت دفاع", R.drawable.vezaratdefae),
            Other("خدمت", R.drawable.khedmat),
            Other("کشاورزی", R.drawable.keshavarzi),
            Other("معلولین", R.drawable.maloolin),
            Other("تاکسی", R.drawable.taxi),
            Other("نیروهای مسلّح", R.drawable.nirohayemosallah),
            Other("پلیس", R.drawable.police),
            Other("عمومی", R.drawable.omoomi),
            Other("تشریفات", R.drawable.tashrifat),
            Other("تاریخی", R.drawable.tarikhi)
        )

        val adapter = OtherAdapter(other)
        otherRecyclerView.adapter = adapter

        return root
    }

}