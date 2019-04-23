package com.hhade.aweb

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val webFragment = WebFragment()
    private val offlineFragment = OfflineFragment()
    private val networkReceiver = ConnectionReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        thread {
            val isConnection = pingInternet()
            handler.post {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, if (isConnection) webFragment else offlineFragment)
                    .commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        networkReceiver.setListener {
            val handler = Handler()
            if (it) {
                thread {
                    val isConnection = pingInternet()
                    handler.post {
                        if (isConnection)
                            goToWeb()
                        else
                            goToOffline()
                    }
                }
            } else {
                goToOffline()
            }
        }
    }

    private fun goToOffline() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, offlineFragment)
            .commit()
    }

    private fun goToWeb() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, webFragment)
            .commit()
    }

    private fun pingInternet(): Boolean {
        var success = false
        try {
            val url = URL("https://google.com")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.connect()
            success = connection.responseCode == 200
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return success
    }

    private val exitDialog = ExitDialog()
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (!exitDialog.isAdded)
                ExitDialog().show(supportFragmentManager, "exit")
            return
        }
        webFragment.goBack()

        doubleBackToExitPressedOnce = true
        Handler().postDelayed(
            { doubleBackToExitPressedOnce = false },
            500
        )
    }
}
