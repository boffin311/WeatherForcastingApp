package com.himanshu.nautiyal.mausam.ui.dashboard

import android.content.Context
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

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private  val TAG="DF";
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.dashboarViewModel.observe(viewLifecycleOwner, Observer {

        })
        val sharedPreference: SharedPreferences =requireActivity().getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE
        )
        Log.d(TAG,sharedPreference.getString("latitude","")+"")
        val lat:Double=sharedPreference.getString("latitude","28.7041")!!.toDouble()
        val lang:Double=sharedPreference.getString("longitude","77.1025")!!.toDouble()
        dashboardViewModel.getSevenDayData(lat,lang,7,SignatureKey.API_KEY)
        return root
    }
}