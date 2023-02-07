package com.mycode.carservice.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.RequestsAdapter
import com.mycode.carservice.adapter.SparePartsAdapter
import com.mycode.carservice.model.SparePartsRequests
import com.mycode.carservice.model.WorkShop

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "userId"



class RequestsFragment : Fragment() {

    val db = Firebase.firestore

    private var userId: String? = null


    private lateinit var  recyclerView:RecyclerView
    private lateinit var adapter: RequestsAdapter

    private lateinit var requestsList:ArrayList<SparePartsRequests>
//    lateinit var titleList:Array<String>
//    lateinit var imgList:Array<Int>
//    lateinit var deliveryDate:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requestsList = arrayListOf()
        datainit()
        recyclerView=view.findViewById(R.id.requests_rv)
        val layoutManager=LinearLayoutManager(requireContext())
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = RequestsAdapter(requestsList)
        recyclerView.adapter= adapter




        adapter.setOnItemClickListener(object :RequestsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                //TODO click the item to go to the requests details
            }
        })

    }

    private fun datainit() {

        requestsList = arrayListOf<SparePartsRequests>()


        Log.d("TAG", "datainit: $userId")
        db.collection("SparePartsRequests")
            .whereEqualTo("requisterId",userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    db.collection("SpareParts")
                        .document(document.data.get("sparePartId").toString().trim())
                        .get()
                        .addOnCompleteListener { sparePartResult ->
                            Log.d("TAG", "${sparePartResult.result.data} => ${sparePartResult.result.data}")
                            val sparePart = sparePartResult.result.data
                            if (sparePart != null) {
                                Log.d("TAG", "${sparePart.get("partName")} && ${document.data.get("arrivalDate")} ${sparePart.get("img")}")

                                val request = SparePartsRequests(
                                    document.id,
                                    sparePart.get("partName").toString(),
                                    document.data.get("arrivalDate").toString(),
                                    sparePart.get("img").toString()
                                )
                                requestsList.add(request)
                                adapter.updateRequestPartsList(requestsList)
                            }




                        }.addOnFailureListener { exception ->
                            Log.w("TAG", "Error getting documents.", exception)
                        }

                }


            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }

    }

//        requestsList= arrayListOf<SparePartsRequests>()
//        titleList= arrayOf("اسم القطعة","اسم القطعة","اسم القطعة")
//        imgList= arrayOf(R.drawable.carsflat,R.drawable.carsflat,R.drawable.carsflat)
//        deliveryDate= arrayOf("23-1-2023","1-2-2023","20-1-2023")
//
//        for (i in titleList.indices){
//            val sparePartsRequests=SparePartsRequests(titleList[i],deliveryDate[i],imgList[i])
//            requestsList.add(sparePartsRequests)
//        }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}