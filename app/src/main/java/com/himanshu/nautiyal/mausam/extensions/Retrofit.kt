package com.himanshu.nautiyal.mausam.extensions

import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

inline fun <T> Call<T>.enqueue(crossinline responseDone:(t:Throwable?, response:Response<T>?) -> Unit){
       this.enqueue(object : Callback<T>{
           override fun onResponse(call: Call<T>, response: Response<T>) {
               Log.d("Sain","Success")
                    responseDone(null,response)
           }

           override fun onFailure(call: Call<T>, t: Throwable) {
               Log.d("Main","failed")
               responseDone(t,null)
           }

       })
}