package com.ltmtlu.revolut.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rate constructor(
    @Json(name = "EUR")
    val eur: Float = 1f,
    @Json(name = "AUD")
    val aud: Float = 1f,
    @Json(name = "BGN")
    val bgn: Float = 1f,
    @Json(name = "BRL")
    val brl: Float = 1f,
    @Json(name = "CAD")
    val cad: Float = 1f,
    @Json(name = "CHF")
    val chf: Float = 1f,
    @Json(name = "CNY")
    val cny: Float = 1f,
    @Json(name = "CZK")
    val czk: Float = 1f,
    @Json(name = "DKK")
    val dkk: Float = 1f,
    @Json(name = "GBP")
    val gbp: Float = 1f,
    @Json(name = "HKD")
    val hkd: Float = 1f,
    @Json(name = "HRK")
    val hrk: Float = 1f,
    @Json(name = "HUF")
    val huf: Float = 1f,
    @Json(name = "IDR")
    val idr: Float = 1f,
    @Json(name = "ILS")
    val ils: Float = 1f,
    @Json(name = "INR")
    val inr: Float = 1f,
    @Json(name = "ISK")
    val isk: Float = 1f,
    @Json(name = "JPY")
    val jpy: Float = 1f,
    @Json(name = "KRW")
    val krw: Float = 1f,
    @Json(name = "MXN")
    val mxn: Float = 1f,
    @Json(name = "MYR")
    val myr: Float = 1f,
    @Json(name = "NOK")
    val nok: Float = 1f,
    @Json(name = "NZD")
    val nzd: Float = 1f,
    @Json(name = "PHP")
    val php: Float = 1f,
    @Json(name = "PLN")
    val pln: Float = 1f,
    @Json(name = "RON")
    val ron: Float = 1f,
    @Json(name = "RUB")
    val rub: Float = 1f,
    @Json(name = "SEK")
    val sek: Float = 1f,
    @Json(name = "SGD")
    val sgd: Float = 1f,
    @Json(name = "THB")
    val thb: Float = 1f,
    @Json(name = "TRY")
    val tryCurrency: Float = 1f,
    @Json(name = "USD")
    val usd: Float = 1f,
    @Json(name = "ZAR")
    val zar: Float = 1f
) : Parcelable

enum class Currency {
    EUR,
    AUD,
    BGN,
    BRL,
    CAD,
    CHF,
    CNY,
    CZK,
    DKK,
    GBP,
    HKD,
    HRK,
    HUF,
    IDR,
    ILS,
    INR,
    ISK,
    JPY,
    KRW,
    MXN,
    MYR,
    NOK,
    NZD,
    PHP,
    PLN,
    RON,
    RUB,
    SEK,
    SGD,
    THB,
    TRY,
    USD,
    ZAR
}