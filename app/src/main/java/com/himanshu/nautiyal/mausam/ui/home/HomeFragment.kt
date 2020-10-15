package com.himanshu.nautiyal.mausam.ui.home

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.himanshu.nautiyal.mausam.ExternalFormulaCalculation
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.home.Adapters.AdapterOtherInfromation
import com.himanshu.nautiyal.mausam.ui.home.models.keyValuePairModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private lateinit var homeViewModel: HomeViewModel
    private val TAG="HF";
    var stateName="Delhi"
    var arrayOfStates= arrayOf("Delhi","Mumbai","Noida")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val sharedPreference:SharedPreferences=requireActivity().getSharedPreferences(resources.getString(R.string.packageName),MODE_PRIVATE)



        root.spinnerGetState.let {
            var setStateAdapter=ArrayAdapter(requireContext(),R.layout.adapter_spinner_get_state,arrayOfStates)
            setStateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            it.adapter=setStateAdapter
            stateName=it.selectedItem.toString()
            it.onItemSelectedListener=this
        }
        root.imageSearch.setOnClickListener{
            homeViewModel.setWeatherByCityName(stateName,SignatureKey.API_KEY)
        }
        homeViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"${it.coord.lat} ${it.coord.lon}")
            root.tvLocationName.text=it.name
            root.tvDateAndTime. text=it.base
            var iconString= it.weather!![0]
            root.imageWeatherStatus.setImageResource(ExternalFormulaCalculation.getImageToLoadAccordingToWeather(iconString.icon))
           if(sharedPreference.getBoolean("type",true)) root.tvTemperature.text=(it.main.temp-273.15).toInt().toString() + " *"
            else root.tvTemperature.text=ExternalFormulaCalculation.getFahrenite(it.main.temp) + " *"
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
        Log.d(TAG,sharedPreference.getString("latitude","")+"")
        val lat:Double=sharedPreference.getString("latitude","28.7041")!!.toDouble()
        val lang:Double=sharedPreference.getString("longitude","77.1025")!!.toDouble()
        homeViewModel.setWeather(lat,lang,SignatureKey.API_KEY)
        return root
    }
    @SuppressLint("SimpleDateFormat")
    private fun getDayAndDate():String{
        val format=SimpleDateFormat("EEEE  HH:MM aaa")
     val calender=Calendar.getInstance()
        val date=format.format(calender.time)
        return date;
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         stateName=arrayOfStates[position]
        Toast.makeText(requireContext(),"here",Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}