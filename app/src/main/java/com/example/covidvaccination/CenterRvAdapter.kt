package com.example.covidvaccination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CenterRvAdapter(private val centerList:List<CenterRVModal>):RecyclerView.Adapter<CenterRvAdapter.CenterRvViewHolder>() {



    class CenterRvViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){


        val centerNameTv : TextView = itemView.findViewById(R.id.idTvCenterName)
        val centerLocationTv :TextView = itemView.findViewById(R.id.idTvLocation)
        val centerTimingsTv :TextView = itemView.findViewById(R.id.idTvTimings)
        val vaccineNameTV :TextView = itemView.findViewById(R.id.idTvVaccineName)
        val feesVaccineTv :TextView = itemView.findViewById(R.id.idTvFees)
        val ageLimitTv :TextView = itemView.findViewById(R.id.idTvAgeLimit)
        val availableTv :TextView = itemView.findViewById(R.id.idTvAvailable)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterRvViewHolder {
     val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center_rv_item,parent,false)
     return  CenterRvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterRvViewHolder, position: Int) {
        val center = centerList[position]
        holder.centerNameTv.text = center.centerName
        holder.centerLocationTv.text = ("Location: " +center.centerAddress)
        holder.centerTimingsTv.text = ("From : " + center.centerFromTime+ "To : " +center.CenterToTime)
        holder.vaccineNameTV.text = center.vaccineName
        holder.feesVaccineTv.text = center.fee_type
        holder.ageLimitTv.text = ("Age Limit: "+center.age_limit.toString())
        holder.availableTv.text =( "Availability: "+center.availableCapacity.toString())

    }

    override fun getItemCount(): Int {
      return  centerList.size
    }


}