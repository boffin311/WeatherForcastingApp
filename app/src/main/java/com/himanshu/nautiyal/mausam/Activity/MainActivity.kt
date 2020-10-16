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
    private lateinit var sharedPreferences: SharedPreferences
    private  var location: Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** To change the color of the status bar*/
        window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=resources.getColor(R.color.colorWhite)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
        sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            MODE_PRIVATE)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /**
        * Basically help in working of the BottomNavigationView using navigation_xml
        * This method is introduced in new Android jetpack Model
        * */
        val navController = findNavController(R.id.nav_host_fragment)
        /** Passing each menu ID as a set of Ids because each
            menu should be considered as top level destinations.*/
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_select_type
        ))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }
    /**
    * it return the Callback to the current location of the user using fusedLocationProviderClient
    * It has two function addOnSuccessListener which is fired when the location access is possible
    * and other onFailureListener which is fired when something went wrong while getting the location
    * */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                }
                return
            }
            else-> return
        }
    }
}