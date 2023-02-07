package com.mycode.carservice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.model.SparePartsRequests
import com.squareup.picasso.Picasso

class RequestsAdapter(private var requestsList:ArrayList<SparePartsRequests>):
    RecyclerView.Adapter<RequestsAdapter.MyHolderRequests> (){

    val db = Firebase.firestore
    private lateinit var mlistener:OnItemClickListener
    //
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    // استدعي هذي الدالة من الفراغمنت
    fun setOnItemClickListener(listener: RequestsAdapter.OnItemClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderRequests {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.requests_rv_layout,parent,false)
        return MyHolderRequests(view,mlistener)
    }

    override fun onBindViewHolder(holder: MyHolderRequests, position: Int) {
        val currentItem=requestsList[position]
        Log.d("TAG", "onBindViewHolder: ${requestsList}")
//        holder.requestImg.setImageResource(R.drawable.carsflat)
        if (currentItem.img.isNotEmpty())
            Picasso.get().load(currentItem.img).resize(1200,800).placeholder(R.drawable.spae_part_ic).into(holder.requestImg)
        holder.requestTitle.text=currentItem.title
        holder.deliveryDate.text=currentItem.deliveryDate
        holder.cancel.setOnClickListener {
            //TODO click cancel to delete the requests
            Log.d("TAG", "onBindViewHolder: ${currentItem.sparePartId}")
            db.collection("SparePartsRequests")
                .document(currentItem.sparePartId.trim()).delete().addOnSuccessListener {
                    Log.d("TAG", "onBindViewHolder ${currentItem.sparePartId} is deleted")
                    requestsList.remove(currentItem)
                    notifyDataSetChanged()
                }.addOnFailureListener{
                    Log.d("TAG", "onBindViewHolder ${it.toString()}")
                }
        }


    }

    override fun getItemCount(): Int {
        return requestsList.size
    }

    class MyHolderRequests(itemView: View,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        var requestTitle:TextView=itemView.findViewById(R.id.requests_title)
        var deliveryDate:TextView=itemView.findViewById(R.id.requests_delivery_date)
        var cancel:TextView=itemView.findViewById(R.id.requests_cancel)
        var requestImg:ImageView=itemView.findViewById(R.id.img_requests)

        init {
            listener.onItemClick(adapterPosition)
        }

    }

    fun updateRequestPartsList(newRequestList:ArrayList<SparePartsRequests>){
        requestsList = newRequestList
        notifyDataSetChanged()
    }
}