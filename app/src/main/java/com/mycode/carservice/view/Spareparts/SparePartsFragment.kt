package com.mycode.carservice.view.Spareparts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.adapter.SparePartsAdapter
import com.mycode.carservice.model.SpareParts

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SparePartsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    val db = Firebase.firestore
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: SparePartsAdapter
    private lateinit var sparePartsList: ArrayList<SpareParts>

    private lateinit var searchView:android.widget.SearchView


//    lateinit var titleList:Array<String>
//    lateinit var typeList:Array<String>
//    lateinit var priceList:Array<String>
//    lateinit var imgList:Array<Int>

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
        return inflater.inflate(R.layout.fragment_spare_parts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datainit()
        val layoutManager= GridLayoutManager(context,2)

        recyclerView=view.findViewById(R.id.spareParts_rv)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= SparePartsAdapter(sparePartsList)
        recyclerView.adapter=adapter
        searchView = view.findViewById(R.id.search_spareParts)

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null)
                {
                    if (p0.isNotEmpty())
                    {
                        val filteredSpareList = sparePartsList.filter {
                            it.title.contains(p0.toString()) || it.price.contains(p0.toString()) || it.type.contains(p0.toString())
                        }
                        adapter.updateSparePartList(filteredSpareList)
                    }else{
                        adapter.updateSparePartList(sparePartsList)
                    }
                }else{
                    adapter.updateSparePartList(sparePartsList)
                }

                return true
            }

        })

        adapter.setOnItemClickListener(object :SparePartsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                loadDetails(sparePartsList[position])

            }
        }
        )

    }

    private fun loadDetails(spareParts: SpareParts){
        val bundle=Bundle()
        bundle.putSerializable("spareParts",spareParts)
        val fragment=SparePartsDetailFragment()

        fragment.arguments=bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun datainit() {
        sparePartsList = arrayListOf<SpareParts>()

//            .whereEqualTo("city", globalAddress!!.subAdminArea)

        db.collection("SpareParts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val documentData = document.data
                    val workShop = SpareParts(
                        document.id,
                        documentData.get("carModel").toString(),
                        documentData.get("Price").toString(),
                        documentData.get("Manufacturer").toString(),
                        documentData.get("img").toString(),
                        documentData.get("city").toString(),
                        documentData.get("partName").toString(),
                        documentData.get("daysToDeliver").toString().toLong()
                    )
                    sparePartsList.add(workShop)
                    Log.w("TAGGG", "${sparePartsList}")

                }
                adapter.updateSparePartList(sparePartsList)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SparePartsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
