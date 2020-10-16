package com.himanshu.nautiyal.mausam.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.himanshu.nautiyal.mausam.R
import kotlinx.android.synthetic.main.activity_starting_screen.*

class StartingScreenActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION=1226;
    private val REQUEST_CHECK_SETTINGS=2728
    private val TAG="SSA";
    private lateinit var sharedPreferences: SharedPreferences
    private  var location:Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
       sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
           MODE_PRIVATE)

        /**
        * btnContinue is set with a listener which check whether the user Provide location permission or not
        * as the location permissions are required to execute the app so we tell the importance of the location to the app
        * and as move to the main activity screen if the permission and the name are provided
        * */
        btnContinue.setOnClickListener {
            when {
                etGetName.text.trim().isEmpty() -> {
                    Toast.makeText(this,"Name Field is Required.",Toast.LENGTH_SHORT).show()
                }
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)-> {
                    Toast.makeText(this,"Please Provide the Location Permission as it is required for the app to show your current weather condition",Toast.LENGTH_LONG).show()
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION)
                }
                else -> {
                    sharedPreferences.edit().putString("userName", etGetName.text.toString().trim()).apply()
                    sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
                    val intent=Intent(this@StartingScreenActivity,MainActivity::class.java)
                    startActivity(intent)
                  this.finish()
                }
            }
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

                }
                return
            }
            else-> return
        }
    }
}