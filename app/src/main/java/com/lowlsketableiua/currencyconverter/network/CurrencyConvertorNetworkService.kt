package com.lowlsketableiua.currencyconverter.network

import com.lowlsketableiua.currencyconverter.network.entities.CurrencyMain
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyConvertorNetworkService {

    @GET("pair/{from}/{to}")
    suspend fun getCourseByCurrency(
        @Path("from") from: String,
        @Path("to") to: String,
    ): Response<CurrencyMain>

    companion object {
        const val BASE_URL = "https://v6.exchangerate-api.com/v6/78f25ca91f2594076dbe0207/"
    }
}