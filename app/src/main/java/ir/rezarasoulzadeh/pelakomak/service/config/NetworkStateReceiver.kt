package ir.rezarasoulzadeh.pelakomak.service.config

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import java.util.ArrayList


class NetworkStateReceiver(context: Context) : BroadcastReceiver() {

    private var mManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var mListeners: MutableList<NetworkStateReceiverListener> = ArrayList()
    private var mConnected: Boolean = false

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.extras == null)
            return

        if (checkStateChanged()) notifyStateToAll()
    }

    private fun checkStateChanged(): Boolean {
        val prev = mConnected
        val activeNetwork = mManager.activeNetworkInfo
        mConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return prev != mConnected
    }

    private fun notifyStateToAll() {
        for (listener in mListeners) {
            notifyState(listener)
        }
    }

    private fun notifyState(listener: NetworkStateReceiverListener?) {
        if (listener != null) {
            if (mConnected)
                listener.onNetworkAvailable()
            else
                listener.onNetworkUnavailable()
        }
    }

    //call this method to add a listener
    fun addListener(l: NetworkStateReceiverListener) {
        mListeners.add(l)
        notifyState(l)
    }

    //call this method to remove a listener
    fun removeListener(l: NetworkStateReceiverListener) {
        mListeners.remove(l)
    }

    interface NetworkStateReceiverListener {
        fun onNetworkAvailable()

        fun onNetworkUnavailable()
    }

    init {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(this, intentFilter)
        checkStateChanged()
    }

}