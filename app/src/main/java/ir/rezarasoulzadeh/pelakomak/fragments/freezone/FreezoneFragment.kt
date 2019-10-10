package ir.rezarasoulzadeh.pelakomak.fragments.freezone

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
import ir.rezarasoulzadeh.pelakomak.list.freezone.Freezone
import ir.rezarasoulzadeh.pelakomak.list.freezone.FreezoneAdapter

class FreezoneFragment : Fragment() {

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_for_freezone, container, false)

        val freezoneRecyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_freezone)
        freezoneRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayout.VERTICAL, false)

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

        return root
    }
}