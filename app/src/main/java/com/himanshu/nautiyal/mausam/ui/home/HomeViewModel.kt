package com.himanshu.nautiyal.mausam.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.nautiyal.mausam.extensions.enqueue
import com.himanshu.nautiyal.mausam.ui.home.models.CurrentWeather

class HomeViewModel : ViewModel() {

    val currentWeatherLiveData: MutableLiveData<CurrentWeather> by lazy {
         MutableLiveData<CurrentWeather>()
    }
    fun setWeather(lat: Double, long:Double, apiKey:String){
        CurrentWeatherClient.currentWeatherApi.getCurrentData(lat,long,apiKey).enqueue { t, response ->
            response?.body()?.let{
                currentWeatherLiveData.postValue(it)
        }
      }
    }
    fun setWeatherByCityName(cityName:String,apiKey: String){
        CurrentWeatherClient.currentWeatherApi.getCurrentDataByCityName(cityName=cityName,api_key= apiKey).enqueue { t, response ->
            response?.body()?.let {
                currentWeatherLiveData.postValue(it)
            }
        }
    }
}