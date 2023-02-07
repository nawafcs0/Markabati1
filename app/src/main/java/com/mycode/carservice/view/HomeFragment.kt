package com.mycode.carservice.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mycode.carservice.R
import com.mycode.carservice.adapter.HomeAdapter
import com.mycode.carservice.model.Services
import com.mycode.carservice.view.SigninSignup.LoginFragment
import com.mycode.carservice.view.Spareparts.SparePartsFragment
import com.mycode.carservice.view.Transportation.CarTransporterFragment
import com.mycode.carservice.view.Workshop.WorkshopFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var servicesList: ArrayList<Services>

    lateinit var titleList:Array<String>
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
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datainit()
//        var button = view.findViewById<Button>(R.id.button)
//        button.setOnClickListener {
//            loadFragment(LoginFragment())
//        }

        val layoutManager=LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.home_rv)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter=HomeAdapter(servicesList)
        recyclerView.adapter=adapter

        // هنا يتم الضغط على عناصر الريسايكلر فيو
        adapter.setOnItemClickListener(object :HomeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //TODO عندما يتم الضغط على عنصر قم بالانتقال اللى الصفحة الخاصة به
//                Toast.makeText(activity, "$position", Toast.LENGTH_SHORT).show()
                if (position==0){
                   loadFragment(WorkshopFragment())
                }
                if (position==1) {
                    loadFragment(CarTransporterFragment())
                }
                if(position==2){
                    loadFragment(SparePartsFragment())
                }
            }
        })


    }

    private fun datainit() {
        servicesList= arrayListOf<Services>()

        //TODO البيانات لابد من اخذها من قاعدة بيانات
        // بيانات تجريبية فقط
        titleList= arrayOf("الورش","الناقلات","قطع الغيار")
        imgList= arrayOf(R.drawable.carsflat,R.drawable.car_trasnporter_ic,R.drawable.spae_part_ic)

        for (i in titleList.indices){
            val service=Services(titleList[i],imgList[i])
            servicesList.add(service)
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}