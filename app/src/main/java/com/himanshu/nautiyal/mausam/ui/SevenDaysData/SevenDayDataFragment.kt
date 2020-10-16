package com.himanshu.nautiyal.mausam.ui.SevenDaysData

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.nautiyal.mausam.CustomDialoges.CustomDialogeProgressBar
import com.himanshu.nautiyal.mausam.ExternalFormulaCalculation
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.SevenDaysData.Adapters.AdapterListSevenDayInfo
import com.himanshu.nautiyal.mausam.ui.SevenDaysData.models.SingleDayModel
import kotlinx.android.synthetic.main.fragment_seven_day_data.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class SevenDayDataFragment : Fragment() {

    lateinit var customDialogProgressBar: CustomDialogeProgressBar
    private lateinit var sevenDayDataViewModel: SevenDayDataViewModel
    private val TAG = "DF";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customDialogProgressBar = CustomDialogeProgressBar(requireContext())
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.show()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sevenDayDataViewModel =
            ViewModelProviders.of(this).get(SevenDayDataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_seven_day_data, container, false)
        val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.packageName),
            Context.MODE_PRIVATE
        )
        /**
         * Setting up the User Name from sharedPreference
         * */
        var name=sharedPreference.getString("userName","Your Name")!!
        if(name.length > 15)
            root.tvUserName.text=name.substring(0,13) + "..."
        else
            root.tvUserName.text = name
        /**
        * It is the a observer to the weather liveData of last seven days as there is any change in the currentWeather model
        * that is we make a request to the server to get the current weather condition or the weather condition
        * */
        sevenDayDataViewModel.sevenDayDataViewModel.observe(viewLifecycleOwner, Observer {
            customDialogProgressBar.dismiss()
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "Something went Wrong. Please connect to internet and TRY AGAIN",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d(TAG, it.cod)

                /**
                * Updating the UI according to the response obtained in the LiveData
                * */
                val list = it.list
                val arrayListSevenDay = ArrayList<SingleDayModel>()
                val typeValue = sharedPreference.getBoolean("type", true)
                /**
                * list contain the arrayList fo the ListItem which need to shown to the user
                * */
                for (singleEle in list!!) {
                    val day = singleEle.dtTxt
                    var minTemp = ExternalFormulaCalculation.getCelcius(singleEle.main.tempMin)
                    var maxTemp = ExternalFormulaCalculation.getCelcius(singleEle.main.tempMax)
                    if (!typeValue) {
                        minTemp = ExternalFormulaCalculation.getFahrenite(singleEle.main.tempMin)
                        maxTemp = ExternalFormulaCalculation.getFahrenite(singleEle.main.tempMin)
                    }
                    val imageStatus = ExternalFormulaCalculation.getImageToLoadAccordingToWeather(singleEle.weather!![0].icon)
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

                /**
                * making a instance of the recyclerView making arrayList of the KeyValuePair
                * which help in showing the data like  WindSpeed, Max and Min temp, Status, Description , etc
                * */
                Log.d(TAG, arrayListSevenDay.size.toString())
                val adapter = AdapterListSevenDayInfo(arrayListSevenDay)
                rvShowSevenDay.layoutManager = LinearLayoutManager(requireActivity())
                rvShowSevenDay.adapter = adapter
            }
        })

        val lat: Double = sharedPreference.getString("latitude", "28.7041")!!.toDouble()
        val lang: Double = sharedPreference.getString("longitude", "77.1025")!!.toDouble()
        sevenDayDataViewModel.getSevenDayData(lat, lang, 7, SignatureKey.API_KEY)
        return root
    }
}