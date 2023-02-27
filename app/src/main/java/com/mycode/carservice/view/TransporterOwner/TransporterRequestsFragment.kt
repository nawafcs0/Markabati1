package com.mycode.carservice.view.TransporterOwner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.TransporterAdapters.TransporterCustomerReqAdapter
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter
import com.mycode.carservice.model.userEmail

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TransporterRequestsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var adapter: TransporterCustomerReqAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var customerReqList: ArrayList<CustomerReqTransporter>

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshop_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datainit()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.workshopCustomerReq_rv)
        recyclerView.layoutManager=layoutManager
        adapter= TransporterCustomerReqAdapter(customerReqList)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter=adapter

        adapter.setOnItemClickListener(object : TransporterCustomerReqAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                loadDetails(customerReqList[position])
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransporterRequestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun datainit() {
        //TODO استقبل الطلبات الخاصة فيك فقط بناءا على ايميل ورشتك
        customerReqList = arrayListOf()
        db.collection("transporter_customerReq")
             .whereEqualTo("transporterEmail", userEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val customerReqlists = CustomerReqTransporter(
                        document.id,
                        documentData.get("customerName").toString(),
                        documentData.get("carName").toString(),
                        documentData.get("customerPhone").toString(),
                        documentData.get("from").toString(),
                        documentData.get("to").toString(),
                        documentData.get("transporterEmail").toString(),
                        documentData.get("transporterId").toString()

                        )
                    customerReqList.add(customerReqlists)

                }
                adapter.updateTransportersList(customerReqList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


    }

    private fun loadDetails(requestsDetails: CustomerReqTransporter){
        val bundle=Bundle()
        bundle.putSerializable("requestDetails",requestsDetails)
        val fragment= TransporterReqDetailFragment()

        fragment.arguments=bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.transporterOwner_frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}