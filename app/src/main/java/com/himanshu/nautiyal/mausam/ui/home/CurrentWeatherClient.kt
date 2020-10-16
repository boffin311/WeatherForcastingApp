package com.himanshu.nautiyal.mausam.ui.home

import com.himanshu.nautiyal.mausam.SignatureKey
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CurrentWeatherClient {
    /**
    * creating the instance of the okHTTPClient
    * */
    private val okHttpClient=OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()
    /**
    * creating the instance of the retrofit with baseUrl as the Url
    * of the server to hit for the response and converting the JSON
    * obtained to POJO or kotlin objects
    * */
    private val retrofitInstance=Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val currentWeatherApi= retrofitInstance.create(CurrentWeatherApi::class.java)
    private val currentWeatherApiUsingCityName= retrofitInstance.create(CurrentWeatherApi::class.java)


}