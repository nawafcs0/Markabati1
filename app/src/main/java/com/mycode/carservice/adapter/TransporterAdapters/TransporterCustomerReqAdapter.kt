package com.mycode.carservice.adapter.TransporterAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter
import com.mycode.carservice.model.WorkshopOwnerModels.CustomerReqWorkshop

class TransporterCustomerReqAdapter(private var customerReqList: List<CustomerReqTransporter> )
    : RecyclerView.Adapter<TransporterCustomerReqAdapter.MyHolderCustomerRequests>() {



    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderCustomerRequests {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.workshop_customer_req_rv,parent,false)
        return MyHolderCustomerRequests(view,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderCustomerRequests, position: Int) {
        val currentItem=customerReqList[position]

        holder.customerName.text=currentItem.customerName
        holder.customerCar.text=currentItem.carName
        holder.customerPhone.text=currentItem.customerPhone


    }

    override fun getItemCount(): Int {
        return customerReqList.size
    }
    class MyHolderCustomerRequests(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val customerName:TextView=itemView.findViewById(R.id.workshopCustomerReq_customer_name)
        val customerCar:TextView=itemView.findViewById(R.id.workshopCustomerReq_carName)
        val customerPhone:TextView=itemView.findViewById(R.id.workshopCustomerReq_phone)

        //click the itemView
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


    }

    fun updateTransportersList(newCustomerReqTransporter:List<CustomerReqTransporter>)
    {
        customerReqList = newCustomerReqTransporter
        notifyDataSetChanged()
    }


}