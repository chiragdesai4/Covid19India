package com.chirag.covid19india.webservice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Chirag Desai
 */
interface ApiInterface {
    @GET
    fun callGetMethod(@Url url: String?): Call<ResponseBody?>?
}