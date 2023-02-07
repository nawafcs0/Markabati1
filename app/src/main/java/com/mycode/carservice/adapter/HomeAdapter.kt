package com.mycode.carservice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.mycode.carservice.R
import com.mycode.carservice.model.Services

class HomeAdapter(private val servicesList:ArrayList<Services>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    // استدعاء لواجهة الريساكلرفيو
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_layout, parent, false)
        return MyViewHolder(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=servicesList[position]
        holder.img.setImageResource(currentItem.img)
        holder.titleImg.text=currentItem.titleImg

    }

    override fun getItemCount(): Int {
        return servicesList.size
    }


    class MyViewHolder(itemView: View,listener:onItemClickListener):RecyclerView.ViewHolder(itemView){
        val titleImg:TextView=itemView.findViewById(R.id.service_title)
        val img:ImageView=itemView.findViewById(R.id.img)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }
}