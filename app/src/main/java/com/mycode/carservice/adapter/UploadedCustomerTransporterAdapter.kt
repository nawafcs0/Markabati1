package com.mycode.carservice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter
import com.mycode.carservice.model.TransporterTable

class UploadedCustomerTransporterAdapter(private var transporterList:List<CustomerReqTransporter>) :
    RecyclerView.Adapter<UploadedCustomerTransporterAdapter.MyHolderWorkshop>() {

    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderWorkshop {
        //TODO تعديل الواجهة الخاصة بصفحى الورش
      val itemView= LayoutInflater.from(parent.context).inflate(R.layout.workshop_rv_layout,parent,false)
        return MyHolderWorkshop(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderWorkshop, position: Int) {
        val currentItem= transporterList[position]
        Log.d("TAG", "onBindViewHolder: ${currentItem}")

        holder.title.text=currentItem.transporterId
        holder.rating.visibility=View.GONE
        holder.location.text=currentItem.from.plus("  الى  "+currentItem.to)



    }

    override fun getItemCount(): Int {
        return transporterList.size
    }

    class MyHolderWorkshop (itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById(R.id.workshop_title)
        val img: ImageView =itemView.findViewById(R.id.img_workshop)
        val location:TextView=itemView.findViewById(R.id.workshop_location)
        val rating:TextView=itemView.findViewById(R.id.workshop_rating)



        //click the itemView
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    fun updateList(newWorkShopList:List<CustomerReqTransporter>)
    {
        transporterList = newWorkShopList
        notifyDataSetChanged()
    }
}