package com.himanshu.nautiyal.mausam.CustomDialoges

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.dialogue_edit_name.*

class CustomDialogeEditName(context:Context,var homeViewModel: HomeViewModel): Dialog(context) {
    var c:Context=context
    var dialogue: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_edit_name)
        /**help to edit the name of the user*/
        btnEdit.setOnClickListener {
            if(etGetName.text.toString().isEmpty()){
                etGetName.error = "The Name Can't Be Empty"
            }
            else {
                homeViewModel.setNameOfTheUser(etGetName.text.toString().trim())
                val sharedPrefernce=context.getSharedPreferences(context.resources.getString(R.string.packageName),Context.MODE_PRIVATE)
                sharedPrefernce.edit().putString("userName",etGetName.text.toString().trim()).apply()
                this.dismiss()
            }
        }
    }


}