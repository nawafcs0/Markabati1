package com.mycode.carservice.adapter.AdminAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.SparePartsCustomerReq
import com.squareup.picasso.Picasso

class UploadedSparePartsAdapter(private var transporterList:List<SparePartsCustomerReq>) :
    RecyclerView.Adapter<UploadedSparePartsAdapter.MyHolder>() {

    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //TODO تعديل الواجهة الخاصة بصفحى الورش
      val itemView= LayoutInflater.from(parent.context).inflate(R.layout.workshop_rv_layout,parent,false)
        return MyHolder(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem= transporterList[position]
        Log.d("TAG", "onBindViewHolder: ${currentItem}")

        holder.title.text=currentItem.requesterName
        holder.rating.visibility=View.GONE
        holder.location.text=currentItem.city
        if(currentItem.img.isNotEmpty())
            Picasso.get().load(currentItem.img).placeholder(R.drawable.spae_part_ic).into(holder.img)



    }

    override fun getItemCount(): Int {
        return transporterList.size
    }

    class MyHolder (itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
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

    fun updateList(newWorkShopList:List<SparePartsCustomerReq>)
    {
        transporterList = newWorkShopList
        notifyDataSetChanged()
    }
}