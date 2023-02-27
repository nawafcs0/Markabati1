package com.mycode.carservice.adapter.TransporterAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.TransporterOwnerModels.AcceptedReqTransporter
import com.mycode.carservice.model.WorkshopOwnerModels.AcceptedReqWorkshop

class TransporterAcceptedReqAdapter(private var acceptedReqList: List<AcceptedReqTransporter> )
    : RecyclerView.Adapter<TransporterAcceptedReqAdapter.MyHolderAcceptedRequests>() {



    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderAcceptedRequests {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.accepted_req_rv,parent,false)
        return MyHolderAcceptedRequests(view,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderAcceptedRequests, position: Int) {
        val currentItem=acceptedReqList[position]

        holder.customerName.text=currentItem.customerName
        holder.customerCar.text=currentItem.carName
        holder.customerPhone.text=currentItem.customerPhone
        holder.customerStatus.text=currentItem.status


    }

    override fun getItemCount(): Int {
        return acceptedReqList.size
    }
    class MyHolderAcceptedRequests(itemView: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val customerName:TextView=itemView.findViewById(R.id.workshopAcceptedReq_customer_name)
        val customerCar:TextView=itemView.findViewById(R.id.workshopAcceptedReq_carName)
        val customerPhone:TextView=itemView.findViewById(R.id.workshopAcceptedReq_phone)
        val customerStatus:TextView=itemView.findViewById(R.id.workshopAcceptedReq_status)

        //click the itemView
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


    }

    fun updateTransportersList(newAcceptedReq:List<AcceptedReqTransporter>)
    {
        acceptedReqList = newAcceptedReq
        notifyDataSetChanged()
    }


}