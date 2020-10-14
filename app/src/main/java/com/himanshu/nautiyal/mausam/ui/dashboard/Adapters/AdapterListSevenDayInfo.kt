package com.himanshu.nautiyal.mausam.ui.dashboard.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.ui.dashboard.models.SingleDayModel
import com.himanshu.nautiyal.mausam.ui.home.Adapters.AdapterOtherInfromation
import kotlinx.android.synthetic.main.adapter_last_seven_day.view.*

class AdapterListSevenDayInfo(var list: ArrayList<SingleDayModel>) : RecyclerView.Adapter<AdapterListSevenDayInfo.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        var tvDay=itemview.tvDay
        var tvHighTemp=itemview.tvHighTemp
        var tvLowTemp=itemview.tvLowTemp
        var tvStatus=itemView.tvStatus
        var imageWeatherStatus=itemview.imageWeatherStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val li=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=li.inflate(R.layout.adapter_last_seven_day,parent,false)
        return AdapterListSevenDayInfo.MyHolder(view);
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

         holder.tvDay.text=getDay(position)
        holder.tvHighTemp.text="${list[position].maxTemp} *"
        holder.tvLowTemp.text="${list[position].minTemp} *"
        holder.imageWeatherStatus.setImageResource(list[position].imageId)
        holder.tvStatus.text=list[position].status

    }

    override fun getItemCount(): Int =list.size
    fun getDay(day:Int):String{
       return  when(day){
            0->"MON"
            1->"TUE"
            2->"WED"
           3->"THU"
           4->"FRI"
           5->"SAT"
           6->"SUN"
           else -> "MON"
        }
    }
}