package com.chirag.covid19india.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import com.chirag.covid19india.util.Logger.d
import java.text.NumberFormat
import java.util.*

object Utils {
    @JvmStatic
    fun isConnectingToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networks = connectivityManager.allNetworks
            var networkInfo: NetworkInfo?
            for (mNetwork in networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork)
                if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED) {
                    d("Network", "NETWORK NAME: " + networkInfo.typeName)
                    return true
                }
            }
        } else {
            val info = connectivityManager.allNetworkInfo
            for (anInfo in info) {
                if (anInfo.state == NetworkInfo.State.CONNECTED) {
                    d("Network", "NETWORK NAME: " + anInfo.typeName)
                    return true
                }
            }
        }
        return false
    }

    fun getFormattedNumber(amount: Double): String {
        return NumberFormat.getNumberInstance(Locale.US).format(amount)
    }
}