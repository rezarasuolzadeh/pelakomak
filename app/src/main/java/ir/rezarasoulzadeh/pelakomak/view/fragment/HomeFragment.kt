package ir.rezarasoulzadeh.pelakomak.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.view.activity.FoulActivity
import kotlinx.android.synthetic.main.content_for_home.view.*
import kotlinx.android.synthetic.main.fragment_for_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_for_home, container, false)

        rootView.foulButtonLayout.setOnClickListener {
            val intent = Intent(rootView.context, FoulActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}