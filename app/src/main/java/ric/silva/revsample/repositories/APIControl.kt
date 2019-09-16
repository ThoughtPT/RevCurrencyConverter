package ric.silva.revsample.repositories

import org.json.JSONObject

class APIControl constructor(serviceInjection: WebRequestInterface): WebRequestInterface {
    private val service: WebRequestInterface = serviceInjection

    override fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.get(path, params, completionHandler)
    }
}