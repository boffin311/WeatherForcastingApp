package com.himanshu.nautiyal.mausam.ui.SevenDaysData

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.nautiyal.mausam.extensions.enqueue
import com.himanshu.nautiyal.mausam.ui.SevenDaysData.models.SevenDayModel

class SevenDayDataViewModel : ViewModel() {

    val sevenDayDataViewModel : MutableLiveData<SevenDayModel> by lazy {
        MutableLiveData<SevenDayModel>()
    }
    /**
    * update the value of the LiveData instance as we created on
    * the base fo response obtained by sevenDayDataApi
    * */
    fun getSevenDayData(lat:Double,lang:Double,cnt:Int,api_key:String){
            SevenDayDataClient.sevenDayDataApi.getSevenDayData(lat,lang,cnt,api_key).enqueue { t, response ->
                Log.d("Main","here hu ")
                response?.body().let {
                     sevenDayDataViewModel.postValue(it)
                }
            }
    }

}