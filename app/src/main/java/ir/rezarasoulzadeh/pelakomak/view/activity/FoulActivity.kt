package ir.rezarasoulzadeh.pelakomak.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.utils.Format
import ir.rezarasoulzadeh.pelakomak.view.adapter.FoulAdapter
import ir.rezarasoulzadeh.pelakomak.viewmodel.FoulViewModel
import kotlinx.android.synthetic.main.activity_foul.*
import kotlinx.android.synthetic.main.dialog_for_sort.view.*
import kotlinx.android.synthetic.main.dialog_for_summary.view.*

class FoulActivity : AppCompatActivity() {

    private lateinit var foulViewModel: FoulViewModel

    private lateinit var plaqueFirstPart: String
    private lateinit var plaqueSecondPart: String
    private lateinit var plaqueThirdPart: String
    private lateinit var plaqueFourthPart: String

    private lateinit var foulCount: String
    private lateinit var foulPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foul)

        supportActionBar!!.hide()

        barcodeEditText.setText(R.string.baba)

        foulViewModel = ViewModelProviders.of(this).get(FoulViewModel::class.java)

        saveButton.setOnClickListener {
            val saveView = LayoutInflater.from(this).inflate(R.layout.dialog_for_summary, null)

            val saveViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(saveView) }

            val saveAlertDialog = saveViewBuilder.show()

            saveAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            saveView.plaqueFirstTextView.text = plaqueFirstPart
            saveView.plaqueSecondTextView.text = plaqueSecondPart
            saveView.plaqueThirdTextView.text = plaqueThirdPart
            saveView.plaqueFourthTextView.text = plaqueFourthPart
            saveView.foulCountTextView.text = foulCount
            saveView.foulPriceTextView.text = foulPrice

        }

        getFoulsButton.setOnClickListener {
            // barcode is 89692969
            val barcode = barcodeEditText.text.toString()
            foulViewModel.provideFoul(barcode)
            foulViewModel.foulLiveData.observe(this, Observer {
                val foulRecyclerView = findViewById<RecyclerView>(R.id.foulRecyclerView)
                val adapter = FoulAdapter(it)
                foulRecyclerView.adapter = adapter
                foulRecyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                val section = plaqueSection(it[0].plaque)
                plaqueFirstPart = section[0]
                plaqueSecondPart = section[1]
                plaqueThirdPart = section[2]
                plaqueFourthPart = section[3]
                foulCount = it.size.toString()
                foulPrice = finallPrice(it)
            })
        }

        sortButton.setOnClickListener {
            val sortView = LayoutInflater.from(this).inflate(R.layout.dialog_for_sort, null)

            val sortViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(sortView) }

            val sortAlertDialog = sortViewBuilder.show()

            sortAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            sortView.recentFoulRadioButton.setOnClickListener {
                Handler().postDelayed({
                    sortAlertDialog.dismiss()
                }, 500)
            }

            sortView.expensiveFoulRadioButton.setOnClickListener {
                Handler().postDelayed({
                    sortAlertDialog.dismiss()
                }, 500)
            }

        }

    }

    private fun plaqueSection(plaque: String): ArrayList<String> {
        val section = ArrayList<String>(4)
        section.add("${plaque[4]}${plaque[5]}")
        section.add("${plaque[3]}")
        section.add("${plaque[0]}${plaque[1]}${plaque[2]}")
        section.add("${plaque[6]}${plaque[7]}")
        return section
    }

    private fun finallPrice(fouls: List<Foul>) : String {
        val format = Format()
        var price = 0
        for (i in fouls.indices) {
            price += fouls[i].amount
        }
        return format.priceFormat(price)
    }

}