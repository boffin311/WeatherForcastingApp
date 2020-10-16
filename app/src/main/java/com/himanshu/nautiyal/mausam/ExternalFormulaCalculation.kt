package com.himanshu.nautiyal.mausam

class ExternalFormulaCalculation {
    companion object{
        /**
         * getting the image of the weather on the bases of response obtained by Api
         * following are the code of different weather condition
         * */
        public fun getImageToLoadAccordingToWeather(imageId:String?):Int{
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
        /**
        * Converting the Kelvin scale to fahrenheit scale
        * */
        public fun getFahrenite(kelvin:Double):String{
            var temp=kelvin - 273.15
            temp *= (9.0 / 5.0)
            temp+=32
            return temp.toInt().toString()
        }
        /**
         * Converting the kelvin scale to Celsius Scale
         * */
        public fun getCelcius(kelvin: Double):String{
            return (kelvin-273.15).toInt().toString()
        }
    }
}