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
import ir.rezarasoulzadeh.pelakomak.view.adapter.FoulAdapter
import ir.rezarasoulzadeh.pelakomak.viewmodel.FoulViewModel
import kotlinx.android.synthetic.main.activity_foul.*
import kotlinx.android.synthetic.main.dialog_for_save.view.*
import kotlinx.android.synthetic.main.dialog_for_sort.view.*

class FoulActivity : AppCompatActivity(){

    private lateinit var foulViewModel: FoulViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foul)

        supportActionBar!!.hide()

        foulViewModel = ViewModelProviders.of(this).get(FoulViewModel::class.java)

        saveButton.setOnClickListener {
            barcodeEditText.setText(R.string.barcode)

            val saveView = LayoutInflater.from(this).inflate(R.layout.dialog_for_save, null)

            val saveViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(saveView) }

            val saveAlertDialog = saveViewBuilder.show()

            saveAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            saveView.pdfButton.setOnClickListener {
                Handler().postDelayed({
                    saveAlertDialog.dismiss()
                }, 500)
            }

            saveView.txtButton.setOnClickListener {
                Handler().postDelayed({
                    saveAlertDialog.dismiss()
                }, 500)
            }
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
}