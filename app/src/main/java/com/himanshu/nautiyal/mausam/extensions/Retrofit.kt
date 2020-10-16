package com.himanshu.nautiyal.mausam.extensions

import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
* It is a generic function to enqueue() function of the  retrofit client
* T is the generic type and we pass inline function as the parameter which is modified on
* the basis of the response from the server i.e. onResponse(for success) and
* onFailure( for failure) of the request send
* */
inline fun <T> Call<T>.enqueue(crossinline responseDone:(t:Throwable?, response:Response<T>?) -> Unit){
       this.enqueue(object : Callback<T>{
           override fun onResponse(call: Call<T>, response: Response<T>) {
                    responseDone(null,response)
           }

           override fun onFailure(call: Call<T>, t: Throwable) {
               responseDone(t,null)
           }

       })
}