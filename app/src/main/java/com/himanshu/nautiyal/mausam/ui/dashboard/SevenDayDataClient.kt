package com.himanshu.nautiyal.mausam.ui.dashboard

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SevenDayDataClient  {
    val okHttpClient=OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()

    var retrofit=Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val sevenDayDataApi= retrofit.create(SevenDayDataApi::class.java)
}