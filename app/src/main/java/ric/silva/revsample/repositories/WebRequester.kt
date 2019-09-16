package ric.silva.revsample.repositories

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import ric.silva.revsample.ConverterApplication
import ric.silva.revsample.utils.PreferenceHelper

class WebRequester : WebRequestInterface{
    val TAG = WebRequester::class.java.simpleName
    val basePath = "https://revolut.duckdns.org/latest?base="

    override fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, basePath + path, params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        ConverterApplication.instance?.addToRequestQueue(jsonObjReq,ConverterApplication.requestTag)
    }
}