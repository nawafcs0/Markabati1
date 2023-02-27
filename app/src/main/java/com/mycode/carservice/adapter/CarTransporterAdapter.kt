package com.mycode.carservice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.CarTransporter
import com.squareup.picasso.Picasso

class CarTransporterAdapter(private var carTransporterList:List<CarTransporter>) :
    RecyclerView.Adapter<CarTransporterAdapter.MyHolderCarTransporter>() {

    private lateinit var mlistener:onItemClickListener
    //
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderCarTransporter {
        //TODO تعديل الواجهة الخاصة بصفحى الورش
      val itemView= LayoutInflater.from(parent.context).inflate(R.layout.car_transporter_rv_layout,parent,false)
        return MyHolderCarTransporter(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderCarTransporter, position: Int) {
        val currentItem=carTransporterList[position]

        holder.title.text=currentItem.name
        holder.price.text=currentItem.price.toString()
        holder.location.text=currentItem.city

        if (currentItem.img.isNotEmpty())
            Picasso.get().load(currentItem.img).placeholder(R.drawable.car_trasnporter_ic).resize(1000,800).into(holder.img);

    }

    override fun getItemCount(): Int {
        return carTransporterList.size
    }

    class MyHolderCarTransporter (itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById(R.id.car_title)
        val img: ImageView =itemView.findViewById(R.id.img_car_transporter)
        val location:TextView=itemView.findViewById(R.id.car_transporter_location)
        val price:TextView=itemView.findViewById(R.id.transporter_price)

        //click the itemView
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }
    fun updateTransportersList(newCarTransporterList:List<CarTransporter>)
    {
        carTransporterList = newCarTransporterList
        notifyDataSetChanged()
    }
}