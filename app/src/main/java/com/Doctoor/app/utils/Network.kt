package com.Doctoor.app.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Checks for network availability
 * NOTE: Don't forget to add android.permission.ACCESS_NETWORK_STATE permission to manifest
 * @return a boolean representing if network is available or not
 */
fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    return network != null && network.isConnected
}