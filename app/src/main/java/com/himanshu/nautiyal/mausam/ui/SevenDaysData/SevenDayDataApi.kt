package com.himanshu.nautiyal.mausam.ui.SevenDaysData

import com.himanshu.nautiyal.mausam.ui.SevenDaysData.models.SevenDayModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SevenDayDataApi {
    /**a GET request to the data of seven days on the bases of current location of the user using lat and lon */
    @GET("forecast")
    fun getSevenDayData(@Query("lat") lat:Double,@Query("lon")lon:Double, @Query("cnt") cnt:Int,@Query("appid")api_key: String):Call<SevenDayModel>

}