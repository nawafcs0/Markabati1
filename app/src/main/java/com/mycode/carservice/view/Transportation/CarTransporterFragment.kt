package com.mycode.carservice.view.Transportation

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
import com.mycode.carservice.adapter.CarTransporterAdapter
import com.mycode.carservice.model.CarTransporter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CarTransporterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val db = Firebase.firestore
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: CarTransporterAdapter
    private lateinit var carTransporterList: ArrayList<CarTransporter>
    private lateinit var searchView:android.widget.SearchView


    lateinit var titleList:Array<String>
    lateinit var locationList:Array<String>
    lateinit var priceList:Array<String>
    lateinit var imgList:Array<Int>

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
        return inflater.inflate(R.layout.fragment_car_transporter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datainit()
        val layoutManager= LinearLayoutManager(context)
//        var btn = view.findViewById<Button>(R.id.addBtn)
//        btn.setOnClickListener {
//            loadFragment(AddTransporterFragment())
//        }
        recyclerView=view.findViewById(R.id.car_transporter_rv)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= CarTransporterAdapter(carTransporterList)
        recyclerView.adapter=adapter

        searchView = view.findViewById(R.id.search_Transportation)
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null)
                {
                    if (p0.isNotEmpty())
                    {
                        val filteredWorkShopList = carTransporterList.filter {
                            it.name.contains(p0.toString()) || it.city.contains(p0.toString())
                        }
                        adapter.updateTransportersList(filteredWorkShopList)
                    }else{
                        adapter.updateTransportersList(carTransporterList)
                    }
                }else{
                    adapter.updateTransportersList(carTransporterList)
                }

                return true
            }

        })

        adapter.setOnItemClickListener(object :CarTransporterAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                loadDetails(carTransporterList[position])
            }
        })
    }

    //TODO send data to CarTransporter details fragment
    private fun loadDetails(transporter: CarTransporter){
        val bundle=Bundle()
        bundle.putSerializable("transporter",transporter)
        val fragment= CarTransporterDetailsFragment()

        fragment.arguments=bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun datainit() {
        carTransporterList = arrayListOf<CarTransporter>()

        db.collection("Transporter")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val transporter = CarTransporter(
                        document.id,
                        documentData.get("name").toString(),
                        documentData.get("city").toString(),
                        documentData.get("price").toString(),
                        documentData.get("rate") as ArrayList<Int>,
                        documentData.get("transporterPhoneNumber").toString(),
                        documentData.get("").toString(),
                        documentData.get("transporterEmail").toString(),
                    )
                    carTransporterList.add(transporter)

                }
                adapter.updateTransportersList(carTransporterList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarTransporterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}