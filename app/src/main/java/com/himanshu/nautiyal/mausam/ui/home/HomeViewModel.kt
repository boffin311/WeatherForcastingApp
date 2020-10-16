package com.himanshu.nautiyal.mausam.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.extensions.enqueue
import com.himanshu.nautiyal.mausam.ui.home.models.CurrentWeather

class HomeViewModel : ViewModel() {
    /**
    * MutableLiveData instance for the CurrentWeather
    * */
    val currentWeatherLiveData: MutableLiveData<CurrentWeather> by lazy {
         MutableLiveData<CurrentWeather>()
    }
    /**
    * MutableLiveData instance for the UserName
    * */
    val nameMutableLiveData:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    /**
    * update the value of the LiveData instance as we created on
    * the base fo response obtained by currentWeatherApi
    * */
    fun setWeather(lat: Double, long:Double, apiKey:String){
        CurrentWeatherClient.currentWeatherApi.getCurrentData(lat,long,apiKey).enqueue { t, response ->
            response?.body().let{
                currentWeatherLiveData.postValue(it)
        }
      }
    }
    /**
    * update the value of the LiveData instance as we created on
    * the base fo response obtained by currentWeatherApi using cityName as the Query Field
    * */
    fun setWeatherByCityName(cityName:String,apiKey: String){
        CurrentWeatherClient.currentWeatherApi.getCurrentDataByCityName(cityName=cityName,api_key= apiKey).enqueue { t, response ->
            response?.body().let {
                currentWeatherLiveData.postValue(it)
            }
        }
    }
    /**
    * Updating the Data of userNameLiveData Instance
    * */
    fun setNameOfTheUser(name:String){
        nameMutableLiveData.postValue(name)
    }
}