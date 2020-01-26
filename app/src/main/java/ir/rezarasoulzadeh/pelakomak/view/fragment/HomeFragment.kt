package ir.rezarasoulzadeh.pelakomak.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.view.activity.FoulActivity
import ir.rezarasoulzadeh.pelakomak.viewmodel.FoulViewModel
import kotlinx.android.synthetic.main.fragment_for_home.*
import kotlinx.android.synthetic.main.fragment_for_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_for_home, container, false)

        root.foulButton.setOnClickListener {
            val intent = Intent(this.context, FoulActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}