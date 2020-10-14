package com.himanshu.nautiyal.mausam.ui.home.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.nautiyal.mausam.R
import com.himanshu.nautiyal.mausam.ui.home.models.keyValuePairModel
import kotlinx.android.synthetic.main.grid_adapter_single_info.view.*

class AdapterOtherInfromation(var arrayOfData:ArrayList<keyValuePairModel>): RecyclerView.Adapter<AdapterOtherInfromation.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

             var tvCategory=itemView.tvCategory
             val tvValue=itemView.tvValue

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
      val li=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view=li.inflate(R.layout.grid_adapter_single_info,parent,false)
        return MyHolder(view);
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvCategory.text=arrayOfData[position].key
        holder.tvValue.text=arrayOfData[position].value
    }

    override fun getItemCount(): Int = arrayOfData.size

}