package com.mycode.carservice.view.Workshop

import android.location.*
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.WorkshopAdapter
import com.mycode.carservice.model.WorkShop
import com.mycode.carservice.model.globalCity
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "userId"


class WorkshopFragment : Fragment(), LocationListener {

    private var userId: String? = null

//    lateinit var binding : FragmentWorkshopBinding
    private lateinit var adapter: WorkshopAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatAction: FloatAction
    private lateinit var workshopsList: ArrayList<WorkShop>
    private lateinit var searchView:android.widget.SearchView

    val db = Firebase.firestore


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
        return inflater.inflate(com.mycode.carservice.R.layout.fragment_workshop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datainit()
        val layoutManager = LinearLayoutManager(context)
//        val btn = view.findViewById<Button>(R.id.addBtn)
//        btn.setOnClickListener {
//            loadFragment(AddWorkShopFragment())
//        }
        recyclerView = view.findViewById(com.mycode.carservice.R.id.workshop_rv)
        //Request location permission
        searchView = view.findViewById(R.id.search_workshop)
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null)
                {
                    if (p0.isNotEmpty())
                    {
                        val filteredWorkShopList = workshopsList.filter {
                            it.name.contains(p0.toString()) || it.city.contains(p0.toString())
                        }
                        adapter.updateList(filteredWorkShopList)
                    }else{
                        adapter.updateList(workshopsList)
                    }
                }else{
                    adapter.updateList(workshopsList)
                }

                return true
            }

        })

        // Already have permission, do the thing
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = WorkshopAdapter(workshopsList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : WorkshopAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val workshop = workshopsList[position]
                loadDetails(workshop)
            }
        })
    }


    private fun loadDetails(workshop: WorkShop) {
        val bundle = Bundle()
//        bundle.putInt("workShop", position)
        bundle.putSerializable("workShop",workshop)
        val fragment = WorkShopDetailsFragment()
        fragment.arguments = bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(com.mycode.carservice.R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun datainit() {
        workshopsList = arrayListOf<WorkShop>()

//            .whereEqualTo("city", globalAddress!!.subAdminArea)

        db.collection("workShops")
            .whereEqualTo("city", "القنفذة")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val workShop = WorkShop(
                        "",
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
                adapter.updateList(workshopsList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                Toast.makeText(context,exception.message,Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            WorkshopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }

    @RequiresApi(33)
    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val latitude = location.latitude
        val longitude = location.longitude
        geocoder.getFromLocation(latitude, longitude, 1) { address ->
            Log.d("TAG", "Location = ${address[0].subAdminArea}")
            globalCity = address[0].subAdminArea

        }
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}