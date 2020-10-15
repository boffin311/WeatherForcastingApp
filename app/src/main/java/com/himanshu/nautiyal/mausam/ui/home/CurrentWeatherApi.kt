package com.himanshu.nautiyal.mausam.ui.home

import com.himanshu.nautiyal.mausam.ui.home.models.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrentWeatherApi {
    @GET("weather")
    fun getCurrentData(@Query("lat")lat:Double, @Query("lon")long:Double, @Query("appid")api_key:String): Call<CurrentWeather>
    @GET("weather")
    fun getCurrentDataByCityName(@Query("q")cityName:String,@Query("appid") api_key:String): Call<CurrentWeather>
}