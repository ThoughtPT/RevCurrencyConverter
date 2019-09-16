package ric.silva.revsample.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ric.silva.revsample.ConverterApplication


class NetworkChangeReceiver(private var callback: (startSchedules: Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if ("android.net.conn.CONNECTIVITY_CHANGE" == intent.action) {
                context?.let {
                    if (ConnectivityHelper.isConnected(it)) {
                        ConverterApplication.instance?.showNetworkToast(true)
                        callback(true)
                    } else {
                        ConverterApplication.instance?.showNetworkToast(false)
                        callback(false)
                    }
                }
            }
        }
    }
}