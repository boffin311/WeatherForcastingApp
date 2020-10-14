package com.himanshu.nautiyal.mausam.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.SignatureKey

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val TAG="HF";

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"${it.coord.lat} ${it.coord.lon}")
        })
        val sharedPreference:SharedPreferences=requireActivity().getSharedPreferences(resources.getString(R.string.packageName),MODE_PRIVATE)
        Log.d(TAG,sharedPreference.getString("latitude","")+"")
        val lat:Double=sharedPreference.getString("latitude","28.7041")!!.toDouble()
        val lang:Double=sharedPreference.getString("longitude","77.1025")!!.toDouble()
        homeViewModel.setWeather(lat,lang,SignatureKey.API_KEY)
        return root
    }
}