package com.crazzylab.rdwallpapers.presentation.global.managers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.Observable

/**
 * Created by Михаил on 16.03.2018.
 */

class NetworkManager(private val context: Context?) {

    fun isInternetOn(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun isInternetOnObservable(): Observable<Boolean> = Observable.just(isInternetOn())

    fun registerReceiver(receiver: BroadcastReceiver){
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(receiver, intentFilter)
    }

    fun unregisterReceiver(receiver: BroadcastReceiver){
        if(receiver != null) context?.unregisterReceiver(receiver)
    }

}