package com.himanshu.nautiyal.mausam.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.nautiyal.mausam.CustomDialoges.CustomDialogeProgressBar
import com.himanshu.nautiyal.mausam.ExternalFormulaCalculation
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.dashboard.Adapters.AdapterListSevenDayInfo
import com.himanshu.nautiyal.mausam.ui.dashboard.models.SingleDayModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*

class DashboardFragment : Fragment() {

    lateinit var customDialogProgressBar: CustomDialogeProgressBar
    private lateinit var dashboardViewModel: DashboardViewModel
    private  val TAG="DF";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customDialogProgressBar= CustomDialogeProgressBar(requireContext())
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.show()

    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val sharedPreference: SharedPreferences =requireActivity().getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE
        )
        dashboardViewModel.dashboarViewModel.observe(viewLifecycleOwner, Observer {
            customDialogProgressBar.dismiss()
            if (it == null){
                Toast.makeText(requireContext(),"Something went Wrong. Please connect to internet and TRY AGAIN",Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d(TAG, it.cod)
            val list = it.list
            val arrayListSevenDay = ArrayList<SingleDayModel>()
            val typeValue = sharedPreference.getBoolean("type", true)
            for (singleEle in list!!) {
                val day = singleEle.dtTxt
                var minTemp = ExternalFormulaCalculation.getCelcius(singleEle.main.tempMin)
                var maxTemp = ExternalFormulaCalculation.getCelcius(singleEle.main.tempMax)
                if (!typeValue) {
                    minTemp = ExternalFormulaCalculation.getFahrenite(singleEle.main.tempMin)
                    maxTemp = ExternalFormulaCalculation.getFahrenite(singleEle.main.tempMin)
                }
                val imageStatus = getImageToLoadAccordingToWeather(singleEle.weather!![0].icon)
                val status = singleEle.weather[0].main

                val singleDay = SingleDayModel(
                    day = day,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    imageId = imageStatus,
                    status = status,
                    type = typeValue
                )
                arrayListSevenDay.add(singleDay)
            }
            Log.d(TAG, arrayListSevenDay.size.toString())
            val adapter = AdapterListSevenDayInfo(arrayListSevenDay)
            rvShowSevenDay.layoutManager = LinearLayoutManager(requireActivity())
            rvShowSevenDay.adapter = adapter
        }
        })

        val lat:Double=sharedPreference.getString("latitude","28.7041")!!.toDouble()
        val lang:Double=sharedPreference.getString("longitude","77.1025")!!.toDouble()
        dashboardViewModel.getSevenDayData(lat,lang,7,SignatureKey.API_KEY)
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
}