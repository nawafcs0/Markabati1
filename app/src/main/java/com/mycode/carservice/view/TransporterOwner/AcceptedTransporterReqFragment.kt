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
import com.mycode.carservice.adapter.TransporterAdapters.TransporterAcceptedReqAdapter
import com.mycode.carservice.adapter.WorkshopAdapters.WorkshopAcceptedReqAdapter
import com.mycode.carservice.model.TransporterOwnerModels.AcceptedReqTransporter
import com.mycode.carservice.model.WorkshopOwnerModels.AcceptedReqWorkshop
import com.mycode.carservice.model.userEmail


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AcceptedTransporterReqFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    private lateinit var adapter: TransporterAcceptedReqAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var acceptedReqList: ArrayList<AcceptedReqTransporter>

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
        return inflater.inflate(R.layout.fragment_transporter_accepted_req, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datainit()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.transporterAcceptedReq_rv)
        recyclerView.layoutManager=layoutManager
        adapter= TransporterAcceptedReqAdapter(acceptedReqList)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter=adapter


        adapter.setOnItemClickListener(object :TransporterAcceptedReqAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //TODO اضغط على تفاصيل الطلب المقبول
//                loadDetails(acceptedReqList[position])
            }
        })

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AcceptedTransporterReqFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun datainit() {
        acceptedReqList = arrayListOf<AcceptedReqTransporter>()
        db.collection("transporter_acceptedReq")
             .whereEqualTo("transporterEmail", userEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val acceptedReqlists = AcceptedReqTransporter(
                        documentData.get("customerName").toString(),
                        documentData.get("carName").toString(),
                        documentData.get("customerPhone").toString(),
                        documentData.get("status").toString(),
                        documentData.get("transporterEmail").toString(),
                        documentData.get("from").toString(),
                        documentData.get("to").toString(),

                    )
                    acceptedReqList.add(acceptedReqlists)

                }
                adapter.updateTransportersList(acceptedReqList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }


    //TODO send data to ًtransporterReqDetail  fragment
    private fun loadDetails(transporter: AcceptedReqWorkshop){
        val bundle=Bundle()
        bundle.putSerializable("requestDetails",transporter)
        val fragment= TransporterReqDetailFragment()

        fragment.arguments=bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.transporterOwner_frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}