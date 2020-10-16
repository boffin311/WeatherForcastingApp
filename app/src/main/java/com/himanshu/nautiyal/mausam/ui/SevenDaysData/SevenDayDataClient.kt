package com.himanshu.nautiyal.mausam.ui.SevenDaysData

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SevenDayDataClient  {
    /**
     * creating the instance of the okHTTPClient
     * */
    val okHttpClient=OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()
    /**
     * creating the instance of the retrofit with baseUrl as the Url
     * of the server to hit for the response and converting the JSON
     * obtained to POJO or kotlin objects
     * */
    var retrofit=Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val sevenDayDataApi= retrofit.create(SevenDayDataApi::class.java)
}