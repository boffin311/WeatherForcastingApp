package com.himanshu.nautiyal.mausam.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.nautiyal.mausam.extensions.enqueue
import com.himanshu.nautiyal.mausam.ui.dashboard.models.SevenDayModel

class DashboardViewModel : ViewModel() {

    val dashboarViewModel : MutableLiveData<SevenDayModel> by lazy {
        MutableLiveData<SevenDayModel>()
    }
    fun getSevenDayData(lat:Double,lang:Double,cnt:Int,api_key:String){
            SevenDayDataClient.sevenDayDataApi.getSevenDayData(lat,lang,cnt,api_key).enqueue { t, response ->
                Log.d("Main","here hu ")
                response?.body().let {
                     dashboarViewModel.postValue(it)
                }
            }
    }

}