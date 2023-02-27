package com.mycode.carservice.view.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.CustomerWorkshopReqAdapter
import com.mycode.carservice.adapter.RequestsAdapter
import com.mycode.carservice.adapter.TransporterAdapters.UploadedTransporterAdapter
import com.mycode.carservice.adapter.UploadedCustomerTransporterAdapter
import com.mycode.carservice.model.SparePartsCustomerReq
import com.mycode.carservice.model.SparePartsRequests
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter
import com.mycode.carservice.model.TransporterTable
import com.mycode.carservice.model.WorkshopOwnerModels.CustomerReqWorkshop

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "userId"



class RequestsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    val db = Firebase.firestore
    lateinit var spinner:Spinner
    lateinit var selectedType:String

    private var userId: String? = null


    private lateinit var  recyclerView:RecyclerView
    private lateinit var adapter: RequestsAdapter
    private lateinit var workshopAdapter: CustomerWorkshopReqAdapter
    private lateinit var transporterAdapter: UploadedCustomerTransporterAdapter

    private lateinit var sparePartrequestsList:ArrayList<SparePartsRequests>
    private lateinit var customerWorkshopReqList: ArrayList<CustomerReqWorkshop>
    private lateinit var transporterList: ArrayList<CustomerReqTransporter>

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
//        datainit()
        spinner=view.findViewById(R.id.customer_requests_spinner)

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.customerRequestType,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }

        spinner.onItemSelectedListener=this

//        loadWorkshopReq()
        recyclerView=view.findViewById(R.id.requests_rv)
        val layoutManager=LinearLayoutManager(requireContext())
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)

    }

    private fun loadSparePartReq() {

        sparePartrequestsList = arrayListOf<SparePartsRequests>()

        db.collection("SparePartsRequests")
            .whereEqualTo("requesterId",userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    db.collection("SpareParts")
                        .document(document.data.get("partId").toString().trim())
                        .get()
                        .addOnCompleteListener { sparePartResult ->
                            Log.d("TAG", "${sparePartResult.result.data} => ${sparePartResult.result.data}")
                            val sparePart = sparePartResult.result.data
                            if (sparePart != null) {
                                Log.d("TAG", "${sparePart.get("partName")} && ${document.data.get("arrivalDate")} ${sparePart.get("img")}")

                                val request = SparePartsRequests(
                                    document.id,
                                    sparePart.get("partName").toString(),
                                    document.data.get("daysToDeliver").toString(),
                                    sparePart.get("img").toString()
                                )
                                sparePartrequestsList.add(request)
                                adapter.updateRequestPartsList(sparePartrequestsList)
                            }


                        }.addOnFailureListener { exception ->
                            Log.w("TAG", "Error getting documents.", exception)
                        }

                }


            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }

    }
    private fun loadWorkshopReq() {

         customerWorkshopReqList = arrayListOf<CustomerReqWorkshop>()
        db.collection("workshop_customerReq")
            .whereEqualTo("requestId", userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val customerReqlists = CustomerReqWorkshop(
                        document.id,
                        documentData.get("customerName").toString(),
                        documentData.get("carName").toString(),
                        documentData.get("customerPhone").toString(),
                        documentData.get("description").toString(),
                        documentData.get("workshopEmail").toString(),
                        documentData.get("workshopName").toString(),

                    )
                    customerWorkshopReqList.add(customerReqlists)

                }
                workshopAdapter.updateList(customerWorkshopReqList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


    }
    private fun transporterRequests() {
        transporterList = arrayListOf()

        db.collection("transporter_customerReq")
            .whereEqualTo("requestId",userId)
            .get()
            .addOnSuccessListener { it->
                for (document in it){
                    val documentData = document.data
                    val customerReqlists = CustomerReqTransporter(
                        documentData.get("requestId").toString(),
                        documentData.get("customerName").toString(),
                        documentData.get("carName").toString(),
                        documentData.get("customerPhone").toString(),
                        documentData.get("from").toString(),
                        documentData.get("to").toString(),
                        documentData.get("transporterEmail").toString(),
                        documentData.get("transporterId").toString(),

                        )
                    transporterList.add(customerReqlists)
                }
                transporterAdapter.updateList(transporterList)

            }


    }



    /*
    1 -تثبيت بيانات صفحة الورشة عند صاحب الورشة
   2- اظهار طلبات العميل التي تتعلق بالورش والسطحات في صفحة طلباتي
   3-
     */

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            selectedType= p0.getItemAtPosition(p2) as String
            when (selectedType) {
                "قطع الغيار" -> {
                    loadSparePartReq()
                    adapter= RequestsAdapter(sparePartrequestsList)
                    recyclerView.adapter=adapter

                    adapter.setOnItemClickListener(object : RequestsAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
//                            val workshop = sparePartrequestsList[position]
////                            loadDetails(workshop)
                        }
                    })
                }
                "ورشة" -> {
                    loadWorkshopReq()
                    workshopAdapter= CustomerWorkshopReqAdapter(customerWorkshopReqList)
                    recyclerView.adapter=workshopAdapter

                    workshopAdapter.setOnItemClickListener(object : CustomerWorkshopReqAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            loadSparePartsDetails(sparePartsList[position])
                        }
                    })
                }
                "ناقلة" -> {
                    transporterRequests()
                    transporterAdapter= UploadedCustomerTransporterAdapter(transporterList )
                    recyclerView.adapter=transporterAdapter

                    transporterAdapter.setOnItemClickListener(object : UploadedCustomerTransporterAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
//                            loadSparePartsDetails(sparePartsList[position])
                        }
                    })

                }
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
