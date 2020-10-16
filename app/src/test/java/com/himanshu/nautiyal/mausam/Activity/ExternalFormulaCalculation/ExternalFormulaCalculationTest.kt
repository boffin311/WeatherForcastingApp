package com.himanshu.nautiyal.mausam.Activity.ExternalFormulaCalculation

import com.himanshu.nautiyal.mausam.ExternalFormulaCalculation
import com.himanshu.nautiyal.mausam.R
import org.junit.Assert
import org.junit.Test

class ExternalFormulaCalculationTest {
    @Test
    fun `get image according to the weather code`(){
        Assert.assertNotNull(ExternalFormulaCalculation.getImageToLoadAccordingToWeather("01d"))
        Assert.assertEquals(ExternalFormulaCalculation.getImageToLoadAccordingToWeather("01d"), R.drawable.clear_sky_day)
    }
    @Test
    fun `convert the kelvin temperature to fahrenheit`(){
        Assert.assertNotNull(ExternalFormulaCalculation.getFahrenite(318.15))
        Assert.assertEquals(ExternalFormulaCalculation.getFahrenite(318.15),"113")
        Assert.assertEquals(ExternalFormulaCalculation.getFahrenite(0.0),"-459")

    }
    @Test
    fun `convert the kelvin temperature to celsius`(){
        Assert.assertNotNull(ExternalFormulaCalculation.getCelcius(318.15))
        Assert.assertEquals(ExternalFormulaCalculation.getCelcius(318.15),"45")
        Assert.assertEquals(ExternalFormulaCalculation.getCelcius(0.0),"-273")

    }
}