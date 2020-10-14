package com.himanshu.nautiyal.mausam.extensions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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