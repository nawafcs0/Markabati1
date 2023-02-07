package com.mycode.carservice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.model.SpareParts
import com.squareup.picasso.Picasso

class SparePartsAdapter(private var sparePartsList:List<SpareParts>) :
    RecyclerView.Adapter<SparePartsAdapter.MyHolderSpareParts>() {

    private lateinit var mlistener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderSpareParts {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.spare_parts_rv_layout,parent,false)
        return MyHolderSpareParts(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderSpareParts, position: Int) {
        val currentItem=sparePartsList[position]
        holder.title.text=currentItem.partName
        holder.title.setSelectAllOnFocus(true)
        holder.type.text=currentItem.type
        holder.type.setSelectAllOnFocus(true)
        holder.price.text=currentItem.price.toString()
        if (currentItem.img.isNotEmpty())
            Picasso.get().load(currentItem.img).resize(1100,800).into(holder.img)
    }

    override fun getItemCount(): Int {
        return sparePartsList.size
    }

    class MyHolderSpareParts(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){

        val title:TextView=itemView.findViewById(R.id.spare_parts_title)
        val type:TextView=itemView.findViewById(R.id.spare_parts_type)
        val img:ImageView=itemView.findViewById(R.id.img_spare_parts)
        val price:TextView=itemView.findViewById(R.id.spare_parts_price)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


    }

    fun updateSparePartList(newSparePartsList:List<SpareParts>){
        sparePartsList = newSparePartsList
        notifyDataSetChanged()
    }


}