package ric.silva.revsample.interactor

import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import ric.silva.revsample.entity.CurrencyItem
import ric.silva.revsample.repositories.APIControl
import ric.silva.revsample.repositories.WebRequester



class ApiTest {
    @Suppress("UNCHECKED_CAST")
    @Test
    fun testPopulateList(){
        val service = WebRequester()
        val apiController = APIControl(service)
        val params = JSONObject()
        apiController.get("EUR", params) { response ->
            Assert.assertNotNull(response)

            val methodPopulate = ConvertRateInteractor::class.java.getDeclaredMethod("populateList")
            methodPopulate.isAccessible = true

            val resultArray = methodPopulate.invoke("Eur", response) as ArrayList<CurrencyItem>
            Assert.assertNotNull(resultArray)
            Assert.assertTrue(resultArray.size > 0)
        }
    }
}