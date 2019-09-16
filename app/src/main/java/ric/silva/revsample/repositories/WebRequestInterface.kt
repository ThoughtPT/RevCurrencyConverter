package ric.silva.revsample.repositories

import org.json.JSONObject

interface WebRequestInterface {
    fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}