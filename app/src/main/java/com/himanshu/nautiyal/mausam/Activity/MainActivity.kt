package com.himanshu.nautiyal.mausam.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.himanshu.nautiyal.mausam.R
import kotlinx.android.synthetic.main.activity_starting_screen.*

class MainActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION=1226;
    private val REQUEST_CHECK_SETTINGS=2728
    private val TAG="SSA";
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private  var location: Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=resources.getColor(R.color.colorWhite)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
        sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            MODE_PRIVATE)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val locationSettingBuilder= LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(locationSettingBuilder.build())

        task.addOnSuccessListener {

            getCurrentLocation()
        }
        task.addOnFailureListener{
            if (it is ResolvableApiException){
                try {

                    it.startResolutionForResult(this@MainActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }

        }
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_select_type
        ))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }
    fun getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                location=it;
                sharedPreferences.edit().putString("latitude",it.latitude.toString()).apply()
                sharedPreferences.edit().putString("longitude",it.longitude.toString()).apply()
                Log.d(TAG,it.latitude.toString())

            }.addOnFailureListener{
                Toast.makeText(this,"Something went wrong Please try after some time", Toast.LENGTH_SHORT).show()
            }

        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==REQUEST_CHECK_SETTINGS){
            getCurrentLocation()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                     getCurrentLocation()
                }
                return
            }
            else-> return
        }
    }
}