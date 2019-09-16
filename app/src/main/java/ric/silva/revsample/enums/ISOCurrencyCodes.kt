package ric.silva.revsample.enums

import ric.silva.revsample.R

@Suppress("unused")
enum class ISOCurrencyCodes(val flagResourceID: Int, val currencyName: String, val decimalPlaces: Int) {
    AUD(R.drawable.flag_aud,"Australia Dollar", 2),
    BGN(R.drawable.flag_bgn,"Bulgaria Lev", 2),
    BRL(R.drawable.flag_brl,"Brazil Real", 2),
    CAD(R.drawable.flag_cad,"Canada Dollar", 2),
    CHF(R.drawable.flag_chf,"Switzerland Franc", 2),
    CNY(R.drawable.flag_cny,"China Yuan Renminbi", 2),
    CZK(R.drawable.flag_czk,"Czech Republic Koruna", 2),
    DKK(R.drawable.flag_dkk,"Denmark Krone", 2),
    EUR(R.drawable.flag_eur, "Euro", 2),
    GBP(R.drawable.flag_gbp,"United Kingdom Pound", 2),
    HKD(R.drawable.flag_hkd,"Hong Kong Dollar", 2),
    HRK(R.drawable.flag_hrk,"Croatia Kuna", 2),
    HUF(R.drawable.flag_huf,"Hungary Forint", 2),
    IDR(R.drawable.flag_idr,"Indonesia Rupiah", 0),
    ILS(R.drawable.flag_ils,"Israel Shekel", 2),
    INR(R.drawable.flag_inr,"India Rupee", 2),
    ISK(R.drawable.flag_isk,"Iceland Krona", 0),
    JPY(R.drawable.flag_jpy,"Japan Yen", 0),
    KRW(R.drawable.flag_krw,"Korea (South) Won", 0),
    MXN(R.drawable.flag_mxn,"Mexico Peso", 2),
    MYR(R.drawable.flag_myr,"Malaysia Ringgit", 2),
    NOK(R.drawable.flag_nok,"Norway Krone", 2),
    NZD(R.drawable.flag_nzd,"New Zealand Dollar", 2),
    PHP(R.drawable.flag_php,"Philippines Peso", 2),
    PLN(R.drawable.flag_pln,"Poland Zloty", 2),
    RON(R.drawable.flag_ron,"Romania Leu", 2),
    RUB(R.drawable.flag_rub,"Russia Ruble", 2),
    SEK(R.drawable.flag_sek,"Sweden Krona", 2),
    SGD(R.drawable.flag_sgd,"Singapore Dollar", 2),
    THB(R.drawable.flag_thb,"Thailand Baht" , 2),
    TRY(R.drawable.flag_try,"Turkey Lira", 2),
    USD(R.drawable.flag_usd,"United States Dollar", 2),
    ZAR(R.drawable.flag_zar,"South Africa Rand", 2);

    companion object {

        fun getCurrency(ISOcode: String?): ISOCurrencyCodes {
            if (ISOcode != null) {
                values().forEach { enumItem ->
                    if (enumItem.name == ISOcode) {
                        return enumItem
                    }
                }
            }
            return EUR
        }

        fun getCurrencyFlagID(ISOcode: String?): Int {
            if (ISOcode != null) {
                values().forEach { enumItem ->
                    if (enumItem.name == ISOcode) {
                        return enumItem.flagResourceID
                    }
                }
            }
            return -1
        }

        fun getCurrencyName(ISOcode: String?): String {
            if (ISOcode != null) {
                values().forEach { enumItem ->
                    if (enumItem.name == ISOcode) {
                        return enumItem.currencyName
                    }
                }
            }
            return ""
        }

        fun getCurrencyDecimalPlaces(ISOcode: String?): Int {
            if (ISOcode != null) {
                values().forEach { enumItem ->
                    if (enumItem.name == ISOcode) {
                        return enumItem.decimalPlaces
                    }
                }
            }
            return 2
        }
    }
}