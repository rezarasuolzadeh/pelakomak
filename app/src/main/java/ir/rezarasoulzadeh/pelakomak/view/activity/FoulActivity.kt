package ir.rezarasoulzadeh.pelakomak.view.activity

import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.config.NetworkStateReceiver
import ir.rezarasoulzadeh.pelakomak.service.utils.Enums
import ir.rezarasoulzadeh.pelakomak.service.utils.Format
import ir.rezarasoulzadeh.pelakomak.service.utils.CustomSnackbar
import ir.rezarasoulzadeh.pelakomak.view.adapter.FoulAdapter
import ir.rezarasoulzadeh.pelakomak.viewmodel.FoulViewModel
import kotlinx.android.synthetic.main.activity_foul.*
import kotlinx.android.synthetic.main.dialog_for_congratulations.view.*
import kotlinx.android.synthetic.main.dialog_for_help.view.*
import kotlinx.android.synthetic.main.dialog_for_network.view.*
import kotlinx.android.synthetic.main.dialog_for_summary.view.*


class FoulActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {

    private var network = true

    private var networkStateReceiver: NetworkStateReceiver? = null

    private val format = Format()
    private val snackbar = CustomSnackbar()

    private lateinit var foulsList: MutableList<Foul>
    private lateinit var foulRecyclerView: RecyclerView
    private lateinit var foulAdapter: FoulAdapter

    private lateinit var foulViewModel: FoulViewModel

    private lateinit var plaqueFirstPart: String
    private lateinit var plaqueSecondPart: String
    private lateinit var plaqueThirdPart: String
    private lateinit var plaqueFourthPart: String
    private lateinit var foulCount: String
    private lateinit var foulPrice: String

    private lateinit var searchView: View
    private lateinit var searchViewBuilder: AlertDialog.Builder
    private lateinit var searchAlertDialog: AlertDialog

    private lateinit var networkView: View
    private lateinit var networkViewBuilder: AlertDialog.Builder
    private lateinit var networkAlertDialog: AlertDialog

    private lateinit var parentView: View

    private lateinit var foulTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foul)

        supportActionBar!!.hide()

        parentView = findViewById<View>(R.id.foulActivityParentLayout)

        ////////////////// search dialog //////////////////////
        searchView = LayoutInflater.from(this).inflate(R.layout.dialog_for_search, null)
        searchViewBuilder =
            this.let { it1 -> AlertDialog.Builder(it1).setView(searchView) }

        searchAlertDialog = searchViewBuilder.create()

        ////////////////// network dialog //////////////////////
        networkView = LayoutInflater.from(this).inflate(R.layout.dialog_for_network, null)

        networkViewBuilder =
            this.let { it1 -> AlertDialog.Builder(it1).setView(networkView) }

        networkAlertDialog = networkViewBuilder.create()

        ///////////////////////////////////////////////////////

        setNetworkStateReceiver()

        barcodeEditText.setText(R.string.baba)

        foulViewModel = ViewModelProviders.of(this).get(FoulViewModel::class.java)

        foulViewModel.foulState.observe(this, Observer {
            if (it == Enums.DataState.LOADING) {
                searchAlertDialog.show()
                searchAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                searchAlertDialog.setCanceledOnTouchOutside(false)
                foulTimer = object : CountDownTimer(15000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        if (it != Enums.DataState.DONE) {
                            snackbar.show(
                                "عملیات با خطا مواجه شد",
                                "short",
                                parentView,
                                this@FoulActivity.layoutInflater
                            )
                            searchAlertDialog.dismiss()
                        }
                    }
                }.start()
            }
            if (it == Enums.DataState.DONE) {
                searchAlertDialog.dismiss()
                foulTimer.cancel()
            }
        })

        summaryButton.setOnClickListener {
            val foulRecyclerView = findViewById<RecyclerView>(R.id.foulRecyclerView)

            if (foulRecyclerView.isEmpty()) {
                snackbar.show(
                    "ابتدا خلافی خود را دریافت کنید",
                    "short",
                    parentView,
                    this.layoutInflater
                )
            } else {
                val saveView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_for_summary, null)

                val saveViewBuilder =
                    this.let { it1 -> AlertDialog.Builder(it1).setView(saveView) }

                val saveAlertDialog = saveViewBuilder.show()

                saveAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                saveView.plaqueFirstTextView.text = plaqueFirstPart
                saveView.plaqueSecondTextView.text = plaqueSecondPart
                saveView.plaqueThirdTextView.text = plaqueThirdPart
                saveView.plaqueFourthTextView.text = plaqueFourthPart
                saveView.foulCountTextView.text = foulCount
                saveView.foulPriceTextView.text = foulPrice

                saveView.summaryCloseButton.setOnClickListener {
                    saveAlertDialog.dismiss()
                }
            }
        }

        getFoulsButton.setOnClickListener {
            if (barcodeEditText.text.toString() == "") {
                snackbar.show(
                    "ابتدا کد ۸ رقمی را وارد کنید",
                    "short",
                    parentView,
                    this.layoutInflater
                )
            } else if (barcodeEditText.text.length < 8) {
                snackbar.show(
                    "کد باید دقیقا ۸ رقم باشد",
                    "short",
                    parentView,
                    this.layoutInflater
                )
            } else {
                val barcode = barcodeEditText.text.toString()
                foulViewModel.provideFoul(barcode)
                foulViewModel.foulLiveData.observe(this, Observer {
                    if (it == null) {
                        searchAlertDialog.dismiss()
                        val congratulationsView = LayoutInflater.from(this)
                            .inflate(R.layout.dialog_for_congratulations, null)
                        val congratulationsViewBuilder = this.let { it1 ->
                            AlertDialog.Builder(it1).setView(congratulationsView)
                        }
                        val congratulationsAlertDialog = congratulationsViewBuilder.show()
                        congratulationsAlertDialog.window!!.setBackgroundDrawable(
                            ColorDrawable(
                                Color.TRANSPARENT
                            )
                        )
                        congratulationsView.congratulationsCloseButton.setOnClickListener {
                            congratulationsAlertDialog.dismiss()
                        }
                    } else {
                        foulsList = it
                        foulRecyclerView = findViewById(R.id.foulRecyclerView)
                        foulAdapter = FoulAdapter(foulsList)
                        foulRecyclerView.adapter = foulAdapter
                        foulRecyclerView.visibility = View.VISIBLE
                        emptyView.visibility = View.GONE
                        val section = format.plaqueSection(it[0].plaque)
                        plaqueFirstPart = section[0]
                        plaqueSecondPart = section[1]
                        plaqueThirdPart = section[2]
                        plaqueFourthPart = section[3]
                        foulCount = format.countFormat(it.size)
                        foulPrice = format.finallPrice(it)
                        searchAlertDialog.dismiss()
                    }
                })
            }
        }

        helpButton.setOnClickListener {
            val helpView = LayoutInflater.from(this).inflate(R.layout.dialog_for_help, null)

            val helpViewBuilder = this.let { it1 -> AlertDialog.Builder(it1).setView(helpView) }

            val helpAlertDialog = helpViewBuilder.show()

            helpAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            helpView.helpCloseButton.setOnClickListener {
                helpAlertDialog.dismiss()
            }

        }

    }

    private fun setNetworkStateReceiver() {
        networkStateReceiver = NetworkStateReceiver(this)
        networkStateReceiver!!.addListener(this)
        applicationContext.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onNetworkAvailable() {
        network = true
    }

    override fun onNetworkUnavailable() {
        try {
            network = false

            networkAlertDialog.show()

            networkAlertDialog.setCanceledOnTouchOutside(false)

            networkAlertDialog.setCancelable(false)

            networkAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            networkView.networkBackButton.setOnClickListener {
                this.finish()
            }

            networkView.networkTryButton.setOnClickListener {
                if (network) {

                    networkAlertDialog.dismiss()
                    searchAlertDialog.dismiss()
                } else {
                    snackbar.show(
                        "اتصال به اینترنت برقرار نیست",
                        "short",
                        parentView,
                        this.layoutInflater
                    )
                }
            }
        } catch (e: Exception) {
            snackbar.show(
                "عملیات با خطا مواجه شد",
                "short",
                parentView,
                this.layoutInflater
            )
        }
    }

}