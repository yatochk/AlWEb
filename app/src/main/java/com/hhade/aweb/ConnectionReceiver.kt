package com.hhade.aweb

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log


class ConnectionReceiver : BroadcastReceiver() {
    private lateinit var listener: (Boolean) -> Unit
    fun setListener(l: (Boolean) -> Unit) {
        listener = l
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val networkInfo = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
            if (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.CONNECTED) {
                listener(true)
                Log.d("Network", "Internet YAY")
            } else if (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
                listener(false)
                Log.d("Network", "No internet :(")
            }
        }
    }
}