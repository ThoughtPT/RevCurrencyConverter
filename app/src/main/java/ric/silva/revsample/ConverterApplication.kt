package ric.silva.revsample

import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ConverterApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)
    }

    fun cancelPendingRequests() {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(requestTag)
        }
    }

    fun showNetworkToast(isConnected: Boolean){
        if(isConnected) {
            Toast.makeText(this, getString(R.string.connectedInternetWarning), Toast.LENGTH_LONG).show()
        } else {
            cancelPendingRequests()
            Toast.makeText(this, getString(R.string.noInternetWarning), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val requestTag = "requestTag"
        private val TAG = ConverterApplication::class.java.simpleName
        @get:Synchronized var instance: ConverterApplication? = null
            private set
    }
}