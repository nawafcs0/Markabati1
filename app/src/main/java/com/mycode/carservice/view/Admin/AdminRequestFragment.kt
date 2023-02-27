package com.mycode.carservice.view.Admin

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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.AdminAdapters.UploadedSparePartsAdapter
import com.mycode.carservice.adapter.TransporterAdapters.UploadedTransporterAdapter
import com.mycode.carservice.adapter.WorkshopAdapters.UploadWorkshopAdapter
import com.mycode.carservice.model.*
import java.time.LocalDate

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminRequestFragment : Fragment() , AdapterView.OnItemSelectedListener {
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var adapterWorkshop: UploadWorkshopAdapter
    private lateinit var adapterTransporter: UploadedTransporterAdapter
    private lateinit var adapterSpareParts: UploadedSparePartsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var workshopsList: ArrayList<WorkShop>
    private lateinit var transporterList: ArrayList<TransporterTable>
    private lateinit var sparePartsList: ArrayList<SparePartsCustomerReq>


    private lateinit var spinner:Spinner
    private var selectedType="قطع الغيار"
    val db=Firebase.firestore

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
        return inflater.inflate(R.layout.fragment_admin_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager= GridLayoutManager(context,2)
        recyclerView=view.findViewById(R.id.admin_requests_rv)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        spinner=view.findViewById(R.id.admin_requests_spinner)



        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.requestType,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener=this



    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminRequestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun workshopRequests() {
         workshopsList = arrayListOf()

//            .whereEqualTo("city", globalAddress!!.subAdminArea)

        db.collection("Workshop_waitingList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    document.id
                    val workShop = WorkShop(
                        document.id,
                        documentData.get("city").toString(),
                        documentData.get("closingHours").toString(),
                        documentData.get("detail").toString(),
                        documentData.get("img").toString(),
                        documentData.get("lastWorkingDay").toString(),
                        documentData.get("name").toString(),
                        documentData.get("openingHours").toString() /* = java.util.ArrayList<kotlin.Boolean> */,
                        documentData.get("rating") as ArrayList<Int>,
                        documentData.get("startWorkingDay").toString(),
                        documentData.get("phoneNumber").toString(),
                        documentData.get("workshopEmail").toString(),

                    )
                    workshopsList.add(workShop)
                    Log.d("TAGTAG", "${document.id} => ${document.data}")
                }
                adapterWorkshop.updateList(workshopsList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                Toast.makeText(context,exception.message,Toast.LENGTH_SHORT).show()
            }
    }

    private fun transporterRequests() {
        transporterList = arrayListOf()

//            .whereEqualTo("city", globalAddress!!.subAdminArea)

        db.collection("Transporter_waitingList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data

                    val workShop = TransporterTable(
                        document.id,
                        documentData.get("name").toString(),
                        documentData.get("city").toString(),
                        documentData.get("price").toString() /* = java.util.ArrayList<kotlin.Int> */,
                        documentData.get("rate") as ArrayList<Int>,
                        documentData.get("transporterPhoneNumber").toString(),
                        documentData.get("transporterEmail").toString(),

                        )
                    transporterList.add(workShop)
                    Log.d("TAGTAG", "${document.id} => ${document.data}")
                }
                adapterTransporter.updateList(transporterList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                Toast.makeText(context,exception.message,Toast.LENGTH_SHORT).show()
            }
    }

    private fun sparePartsRequests() {

        //TODO get the cutomer spare requests
        sparePartsList = arrayListOf<SparePartsCustomerReq>()
        db.collection("SparePartsRequests")
            .get()
            .addOnSuccessListener { result->
                if (!result.isEmpty){
                    for (document in result){
                        val documentData=document.data
                        var sparePart=SparePartsCustomerReq(
                            documentData.get("partId").toString(),
                            documentData.get("partName").toString(),
                            documentData.get("price").toString(),
                            documentData.get("type").toString(),
                            documentData.get("img").toString(),
                            documentData.get("city").toString(),
                            documentData.get("requesterName").toString(),
                            documentData.get("requesterId").toString(),
                            documentData.get("requesterEmail").toString(),
                            documentData.get("daysToDeliver").toString(),

                        )
                        sparePartsList.add(sparePart)
                        adapterSpareParts.updateList(sparePartsList)
                    }
                }else{
                    Toast.makeText(context,"fwfwfwe",Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
            }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            selectedType= p0.getItemAtPosition(p2) as String
            when (selectedType) {
                "ورشة" -> {
                    workshopRequests()
                    adapterWorkshop= UploadWorkshopAdapter(workshopsList)
                    recyclerView.adapter=adapterWorkshop

                    adapterWorkshop.setOnItemClickListener(object : UploadWorkshopAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val workshop = workshopsList[position]
                            loadDetails(workshop)
                        }
                    })
                }
                "قطع الغيار" -> {
                    // TODO استعرض طلبات القطع من قل العملاء
                    sparePartsRequests()
                    adapterSpareParts= UploadedSparePartsAdapter(sparePartsList)
                    recyclerView.adapter=adapterSpareParts

                    adapterSpareParts.setOnItemClickListener(object : UploadedSparePartsAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val spare= sparePartsList[position]

                            loadSparePartsDetails(spare)
                        }
                    })
                }
                "ناقلة" -> {
                    transporterRequests()
                    adapterTransporter= UploadedTransporterAdapter(transporterList)
                    recyclerView.adapter=adapterTransporter

                    adapterTransporter.setOnItemClickListener(object :UploadedTransporterAdapter.onItemClickListener{

                        override fun onItemClick(position: Int) {
                            val transporter= transporterList[position]
                            loadTransporterReqDetails(transporter)
                        }
                    })

                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun loadDetails(workshop: WorkShop) {
        val bundle = Bundle()
//        bundle.putInt("workShop", position)
        bundle.putSerializable("workShop",workshop)
        val fragment = RequestDetailFragment()
        fragment.arguments = bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(com.mycode.carservice.R.id.admin_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun loadSparePartsDetails(spareParts:SparePartsCustomerReq) {
        val bundle = Bundle()
        bundle.putSerializable("spareParts",spareParts)
        val fragment = RequestSpareParteDetailFragment()
        fragment.arguments = bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(com.mycode.carservice.R.id.admin_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun loadTransporterReqDetails(transporter: TransporterTable) {
        val bundle = Bundle()
        bundle.putSerializable("transporter",transporter)
        val fragment = RequestTransporterDetailFragment()
        fragment.arguments = bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(com.mycode.carservice.R.id.admin_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}