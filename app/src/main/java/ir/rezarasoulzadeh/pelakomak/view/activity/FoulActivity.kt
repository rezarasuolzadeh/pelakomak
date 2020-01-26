package ir.rezarasoulzadeh.pelakomak.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.view.adapter.FoulAdapter
import ir.rezarasoulzadeh.pelakomak.viewmodel.FoulViewModel

class FoulActivity : AppCompatActivity(){

    private lateinit var foulViewModel: FoulViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foul)

        supportActionBar!!.hide()

        foulViewModel = ViewModelProviders.of(this).get(FoulViewModel::class.java)

        foulViewModel.provideFoul("89692969")
        foulViewModel.foulLiveData.observe(this, Observer {
            val foulRecyclerView = findViewById<RecyclerView>(R.id.foulRecyclerView)
            val adapter = FoulAdapter(it)
            foulRecyclerView.adapter = adapter
        })

    }

}