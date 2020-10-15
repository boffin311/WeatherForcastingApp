package com.himanshu.nautiyal.mausam.CustomDialoges

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.himanshu.nautiyal.mausam.R


class CustomDialogeProgressBar(a: Context) : Dialog(a) {
    var c: Context
    var d: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_view_progress)


    }

    init {
        c = a
    }
}
