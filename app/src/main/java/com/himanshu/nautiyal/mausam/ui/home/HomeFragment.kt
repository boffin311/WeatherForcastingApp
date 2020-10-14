package com.himanshu.nautiyal.mausam.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.home.Adapters.AdapterOtherInfromation
import com.himanshu.nautiyal.mausam.ui.home.models.keyValuePairModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val TAG="HF";

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"${it.coord.lat} ${it.coord.lon}")
            root.tvLocationName.text=it.name
            root.tvDateAndTime. text=it.base
            var iconString= it.weather!![0]
            root.imageWeatherStatus.setImageResource(getImageToLoadAccordingToWeather(iconString.icon))
            root.tvTemperature.text=it.main.temp.toString()
            root.tvStatus.text=iconString.main
            root.tvDateAndTime.text=getDayAndDate()
            root.rvShowData.layoutManager=GridLayoutManager(requireActivity(),3)
            var arrayOfData= arrayListOf(keyValuePairModel("Pressure",it.main.pressure.toString()),
             keyValuePairModel("Visibility",it.visibility.toString()),
             keyValuePairModel("Humidity",it.main.humidity.toString()),
             keyValuePairModel("Wind",it.wind.speed.toString()),
             keyValuePairModel("Max Temp",it.main.tempMax.toString()),
             keyValuePairModel("Min Temp",it.main.tempMin.toString())
            )
            val adapter=AdapterOtherInfromation(arrayOfData)
            root.rvShowData.adapter=adapter

        })
        val sharedPreference:SharedPreferences=requireActivity().getSharedPreferences(resources.getString(R.string.packageName),MODE_PRIVATE)
        Log.d(TAG,sharedPreference.getString("latitude","")+"")
        val lat:Double=sharedPreference.getString("latitude","28.7041")!!.toDouble()
        val lang:Double=sharedPreference.getString("longitude","77.1025")!!.toDouble()
        homeViewModel.setWeather(lat,lang,SignatureKey.API_KEY)
        return root
    }
    private fun getImageToLoadAccordingToWeather(imageId:String?):Int{
        return when(imageId){
            "01d"->R.drawable.clear_sky_day
            "01n"->R.drawable.clear_sky_night
            "02d"->R.drawable.few_cloud_day
            "02n"->R.drawable.few_cloud_night
            "03d"->R.drawable.scattered_cloud_day
            "03n"->R.drawable.scattered_cloud_night
            "04d"->R.drawable.broken_cloud_day
            "04n"->R.drawable.broken_cloud_night
            "09d"->R.drawable.shower_rain_day
            "09n"->R.drawable.shower_rain_day
            "10d"->R.drawable.rain_day
            "10n"->R.drawable.rain_night
            "11d"->R.drawable.thunder_storm_day
            "11n"->R.drawable.thunder_storm_day
            "13d"->R.drawable.snow_day
            "13n"->R.drawable.snow_day
            "50d"->R.drawable.mist_day
            "50n"->R.drawable.mist_night
            else->R.drawable.clear_sky_day
        }
    }
    private fun getDayAndDate():String{
        val format=SimpleDateFormat("EEEE  HH:MM aaa")
     val calender=Calendar.getInstance()
        val date=format.format(calender.time)
        return date;
    }
}