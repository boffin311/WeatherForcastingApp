package com.himanshu.nautiyal.mausam.ui.dashboard.models

import com.google.gson.annotations.SerializedName
import com.himanshu.nautiyal.mausam.ui.commonModels.Clouds
import com.himanshu.nautiyal.mausam.ui.commonModels.WeatherItem
import com.himanshu.nautiyal.mausam.ui.commonModels.Wind

data class ListItem(@SerializedName("dt")
                    val dt: Int = 0,
                    @SerializedName("pop")
                    val pop: Int = 0,
                    @SerializedName("visibility")
                    val visibility: Int = 0,
                    @SerializedName("dt_txt")
                    val dtTxt: String = "",
                    @SerializedName("weather")
                    val weather: List<WeatherItem>?,
                    @SerializedName("main")
                    val main: Main,
                    @SerializedName("clouds")
                    val clouds: Clouds,
                    @SerializedName("sys")
                    val sys: Sys,
                    @SerializedName("wind")
                    val wind: Wind
)