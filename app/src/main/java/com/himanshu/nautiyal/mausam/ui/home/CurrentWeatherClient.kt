package com.himanshu.nautiyal.mausam.ui.home

import com.himanshu.nautiyal.mausam.SignatureKey
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CurrentWeatherClient {
    val okHttpClient=OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()
    val retrofitInstance=Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val currentWeatherApi= retrofitInstance.create(CurrentWeatherApi::class.java)

}