package com.himanshu.nautiyal.mausam.ui.selectType

import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.himanshu.nautiyal.mausam.R
import kotlinx.android.synthetic.main.fragment_select_type.*
import kotlinx.android.synthetic.main.fragment_select_type.view.*

class FragmentSelectType : Fragment() {

    companion object {
        fun newInstance() = FragmentSelectType()
    }

    private lateinit var viewModel: FragmentSelectTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root=inflater.inflate(R.layout.fragment_select_type, container, false)
        var sharedPreferences=requireActivity().getSharedPreferences(resources.getString(R.string.packageName),MODE_PRIVATE)
        var type=sharedPreferences.getBoolean("type",true)
        if(type) changeStateAccordingToClick(root.cardFahrenite,root.cardCelcius)
        else changeStateAccordingToClick(root.cardCelcius,root.cardFahrenite)
        root.cardCelcius.setOnClickListener {
            sharedPreferences.edit().putBoolean("type",true).apply()
            changeStateAccordingToClick(root.cardFahrenite,root.cardCelcius)
        }
        root.cardFahrenite.setOnClickListener {
            sharedPreferences.edit().putBoolean("type",false).apply();
           changeStateAccordingToClick(root.cardCelcius,root.cardFahrenite)
        }
        return root
    }
     fun changeStateAccordingToClick(cardView1:CardView,cardView2:CardView){

         cardView1.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
         cardView2.setCardBackgroundColor(resources.getColor(R.color.colorSelected))
         cardView2.alpha=1f
         cardView1.alpha=0.5f
     }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentSelectTypeViewModel::class.java)

    }

}