package com.himanshu.nautiyal.mausam.ui.selectType

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.himanshu.nautiyal.mausam.R

class FragmentSelectType : Fragment() {

    companion object {
        fun newInstance() = FragmentSelectType()
    }

    private lateinit var viewModel: FragmentSelectTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_type, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentSelectTypeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}