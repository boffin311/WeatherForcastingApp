package com.himanshu.nautiyal.mausam.Activity.DashBoardActivity

import com.himanshu.nautiyal.mausam.ui.SevenDaysData.SevenDayDataClient
import org.junit.Assert
import org.junit.Test

class SevenDayDataResponseTest {
    @Test
    fun `get response from  seven day data hit point`(){
        var response=SevenDayDataClient.sevenDayDataApi.getSevenDayData(28.704,77.1025, 7,"2bfddb3d0f0062143837f34fdf64c723")
            .execute()
        response.body()?.let {
             Assert.assertNotNull(it)
             Assert.assertEquals("200",it.cod)
            Assert.assertEquals(2000,it.city.population)
        }
    }
}