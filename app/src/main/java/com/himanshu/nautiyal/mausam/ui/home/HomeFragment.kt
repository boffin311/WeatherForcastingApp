package com.himanshu.nautiyal.mausam.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.himanshu.nautiyal.mausam.CustomDialoges.CustomDialgeDatePicker
import com.himanshu.nautiyal.mausam.CustomDialoges.CustomDialogeEditName
import com.himanshu.nautiyal.mausam.CustomDialoges.CustomDialogeProgressBar
import com.himanshu.nautiyal.mausam.ExternalFormulaCalculation
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey
import com.himanshu.nautiyal.mausam.ui.home.Adapters.AdapterOtherInfromation
import com.himanshu.nautiyal.mausam.ui.home.models.keyValuePairModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val LOCATION_PERMISSION=1226;
    private val REQUEST_CHECK_SETTINGS=2728
    lateinit var customDialogProgressBar: CustomDialogeProgressBar
    private lateinit var homeViewModel: HomeViewModel
    private val TAG = "HF";

    private lateinit var sharedPreferences: SharedPreferences
    var stateName = "Delhi"
    var arrayOfStates = arrayOf("Delhi", "Mumbai", "Noida")
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private  var location: Location?=null
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
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
       sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.packageName),
            MODE_PRIVATE
        )
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(requireActivity())

        var name=sharedPreferences.getString("userName","Name")!!
        if(name.length > 15)
            root.tvUserName.text=name.substring(0,13) + "..."
        else
            root.tvUserName.text = name

        /**
        * SpinnerGetState used to choose the name of the city for which we have to get the
        * data
        * */
        root.spinnerGetState.let {
            var setStateAdapter =
                ArrayAdapter(requireContext(), R.layout.adapter_spinner_get_state, arrayOfStates)
            setStateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            it.adapter = setStateAdapter
            stateName = it.selectedItem.toString()
            it.onItemSelectedListener = this
        }
        /**
        * imageSearch is used to request the server to get the data for the response on the bases
        * of city name and than updating the homeViewModel accordingly
        * */
        root.imageSearch.setOnClickListener {
            customDialogProgressBar.show()
            homeViewModel.setWeatherByCityName(stateName, SignatureKey.API_KEY)
        }
        /**
        * Pick the date for which the response has to be made
        * */
        root.imagePickDate.setOnClickListener {
            var customDialogeDatePicker=CustomDialgeDatePicker(requireContext())
            customDialogeDatePicker.show()
        }
        /**
        * help in editing the ame of the user
        * */
        root.imageEditName.setOnClickListener {
            var customDialgougeEditNamed=CustomDialogeEditName(requireContext(),homeViewModel)
            customDialgougeEditNamed.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            customDialgougeEditNamed.show()
        }
        /**
        * liveData attached to the user name as soon we change the name of the user
        * the viewModel change the LiveData and the change is reflected on the screen
        * */
        homeViewModel.nameMutableLiveData.observe(viewLifecycleOwner,{
            if(it.length > 15)
                root.tvUserName.text=it.substring(0,13) + "..."
            else
            root.tvUserName.text = it

        })
        /**
        * It is the a observer to the weather liveData as there is any change in the currentWeather model
        * that is we make a request to the server to get the current weather condition or the weather condition
        * with respect to cityName
        * */
        homeViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"Here hu ")
            customDialogProgressBar.dismiss()
            if (it == null) {
                Toast.makeText(requireContext(),"Something went wrong. Please Connect to internet and TRY AGAIN",Toast.LENGTH_SHORT).show()
            } else {

                /**
                * Updating the UI according to the response obtained in the LiveData
                * */
                Log.d(TAG, "${it.coord.lat} ${it.coord.lon}")
                root.tvLocationName.text = it.name
                root.tvDateAndTime.text = it.base
                var iconString = it.weather!![0]
                root.imageWeatherStatus.setImageResource(
                    ExternalFormulaCalculation.getImageToLoadAccordingToWeather(
                        iconString.icon
                    )
                )
                if (sharedPreferences.getBoolean("type", true)) root.tvTemperature.text =
                    (it.main.temp - 273.15).toInt().toString() + "°"
                else root.tvTemperature.text =
                    ExternalFormulaCalculation.getFahrenite(it.main.temp) + "°"

                /**
                * making a instance of the recyclerView making arrayList of the KeyValuePair
                * which help in showing the data like Pressure, Humidity, WindSpeed, Max and Min temp etc
                * */
                root.tvStatus.text = iconString.main
                root.tvDateAndTime.text = getDayAndDate()
                root.rvShowData.layoutManager = GridLayoutManager(requireActivity(), 3)
                var arrayOfData = arrayListOf(
                    keyValuePairModel("Pressure", it.main.pressure.toString()),
                    keyValuePairModel("Visibility", it.visibility.toString()),
                    keyValuePairModel("Humidity", it.main.humidity.toString()),
                    keyValuePairModel("Wind", it.wind.speed.toString()),
                    keyValuePairModel("Max Temp", it.main.tempMax.toString()),
                    keyValuePairModel("Min Temp", it.main.tempMin.toString())
                )
                val adapter = AdapterOtherInfromation(arrayOfData)
                root.rvShowData.adapter = adapter
            }
        })
        /**
         * Setting up the current location using google location Api
         * It usse the FusedLocation Provider Client to obtain the location
         * even if you are inside a building ( i.e. by network location client ) or by
         * using GPS
         * */
        val locationSettingBuilder= LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(locationSettingBuilder.build())

        task.addOnSuccessListener {

            getCurrentLocation()
        }
        task.addOnFailureListener{
            if (it is ResolvableApiException){
                try {

                    it.startResolutionForResult(requireActivity(),
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }

        }

        return root
    }

    @SuppressLint("SimpleDateFormat")

    /**
    * Formatting the Current Date according to the format
    * EEEE -> {Day Name: "Sunday", "Monday","Tuesday", etc}
    * HH   -> { Hours of the day 0..24}
    * MM   -> { Min of the current time 0..60}
    * aaa  -> { State of the day AM or PM}
    * */
    private fun getDayAndDate(): String {
        val format = SimpleDateFormat("EEEE  HH:MM aaa")
        val calender = Calendar.getInstance()
        val date = format.format(calender.time)
        return date;
    }
   /**
   * Listener to the change of data inside the spinner i.e. if you
   * select a particular item from the Spinner onItemSelected will be executed
   * */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        stateName = arrayOfStates[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    /**
     * it return the Callback to the current location of the user using fusedLocationProviderClient
     * It has two function addOnSuccessListener which is fired when the location access is possible
     * and other onFailureListener which is fired when something went wrong while getting the location
     * */
    fun getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                location=it;
                sharedPreferences.edit().putString("latitude",it.latitude.toString()).apply()
                sharedPreferences.edit().putString("longitude",it.longitude.toString()).apply()
                val lat: Double = sharedPreferences.getString("latitude", "28.7041")!!.toDouble()
                val lang: Double = sharedPreferences.getString("longitude", "77.1025")!!.toDouble()
                homeViewModel.setWeather(lat, lang, SignatureKey.API_KEY)

            }.addOnFailureListener{
                Toast.makeText(requireActivity(),"Something went wrong Please try after some time", Toast.LENGTH_SHORT).show()
            }

        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== AppCompatActivity.RESULT_OK && requestCode==REQUEST_CHECK_SETTINGS){
            getCurrentLocation()
        }
    }

}