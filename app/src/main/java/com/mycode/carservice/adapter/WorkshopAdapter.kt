package com.mycode.carservice.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycode.carservice.R
import com.mycode.carservice.model.Services
import com.mycode.carservice.model.WorkShop
import com.mycode.carservice.model.Workshops
import com.mycode.carservice.view.MainActivity
import com.mycode.carservice.view.Workshop.WorkshopFragment
import com.squareup.picasso.Picasso

class WorkshopAdapter(private var workshopList:List<WorkShop>) :
    RecyclerView.Adapter<WorkshopAdapter.MyHolderWorkshop>() {

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
        val currentItem=workshopList[position]
        Log.d("TAG", "onBindViewHolder: ${currentItem}")

        holder.title.text=currentItem.name
        holder.rating.text=currentItem.rating.toString()
        holder.location.text=currentItem.city
        if (currentItem.img.isNotEmpty())
            //TODO get the workshop image from the database
            Picasso.get().load(currentItem.img).resize(1000,800).into(holder.img);



    }

    override fun getItemCount(): Int {
        return workshopList.size
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

    fun updateList(newWorkShopList:List<WorkShop>)
    {
        workshopList = newWorkShopList
        notifyDataSetChanged()
    }
}