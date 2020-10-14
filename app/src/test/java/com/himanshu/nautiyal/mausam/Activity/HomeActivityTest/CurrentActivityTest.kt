package com.himanshu.nautiyal.mausam.Activity.HomeActivityTest

import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.home.CurrentWeatherClient
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class CurrentActivityTest {
    @Test
    fun `get the response of current data `(){
        val response=CurrentWeatherClient.currentWeatherApi.getCurrentData(28.7041,77.1025, SignatureKey.API_KEY).execute();
        response.body()?.let {
//            assertNotNull(it)
            assertEquals(200,it.cod)
        }
    }
}