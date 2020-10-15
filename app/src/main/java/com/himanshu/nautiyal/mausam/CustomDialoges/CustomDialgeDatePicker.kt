package com.himanshu.nautiyal.mausam.CustomDialoges

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.himanshu.nautiyal.mausam.R
import kotlinx.android.synthetic.main.dialogue_date_picker.*

class CustomDialgeDatePicker(context:Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialogue_date_picker)
        tvOk.setOnClickListener{dismiss()}
    }
}