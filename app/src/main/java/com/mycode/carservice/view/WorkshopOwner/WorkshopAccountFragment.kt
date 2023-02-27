package com.mycode.carservice.view.WorkshopOwner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.model.*
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class WorkshopAccountFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var workshopName:EditText
    private lateinit var workshopCity:EditText
    private lateinit var workshopShours:EditText
    private lateinit var workshopEhours:EditText
    private lateinit var workshopSDay:EditText
    private lateinit var workshopEDay:EditText
    private lateinit var workshopDescription:EditText
    private lateinit var workshopPhone:EditText
    private lateinit var workshopImg:ImageView
    private lateinit var workshopSave:Button


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
        return inflater.inflate(R.layout.fragment_workshop_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workshopName=view.findViewById(R.id.workshopOwner_name)
        workshopCity=view.findViewById(R.id.workshopOwner_city)
        workshopShours=view.findViewById(R.id.workshopOwner_startHorus)
        workshopEhours=view.findViewById(R.id.workshopOwner_endHouras)
        workshopSDay=view.findViewById(R.id.workshopOwner_startDay)
        workshopEDay=view.findViewById(R.id.workshopOwner_endDay)
        workshopDescription=view.findViewById(R.id.workshopOwner_desc)
        workshopSave=view.findViewById(R.id.workshopOwner_btnSave)
        workshopPhone=view.findViewById(R.id.workshopOwner_phone)
        workshopImg=view.findViewById(R.id.img_workshopOwner)


        var name =workshopName.text
        var city =workshopCity.text
        var phone=workshopPhone.text
        var  sHours=workshopShours.text
        var  eHours=workshopEhours.text
        var  sDay=workshopSDay.text
        var  eDay=workshopEDay.text
        var  desc=workshopDescription.text

        //TODO ثبت ببيانات الورشة بعد رفع الطلب الى الادمن
        datainita()

        workshopSave.setOnClickListener{
            //
            if (name.isNotEmpty()&&city.isNotEmpty()&&desc.isNotEmpty()&&sHours.isNotEmpty()&&
                phone.isNotEmpty()&&eHours.isNotEmpty()&&sDay.isNotEmpty()&&eDay.isNotEmpty()){
                var rating = ArrayList<Int>()
                //TODO نحتاج نرفع صورة للورشة
                add(WorkShop(
                    "",
                    city.toString(),
                    eHours.toString(),
                    desc.toString(),
                    ""
                    ,
                    eDay.toString(),
                    name.toString(),
                    sHours.toString(),
                    rating,
                    eDay.toString(),
                    phone.toString(),
                    userEmail
                ))


            }else{
                Toast.makeText(requireContext(),"جميع الحقول مطلوبة", Toast.LENGTH_SHORT).show()
            }
        }





    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkshopAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun datainita() {

        db.collection("workShops")
            .whereEqualTo("workshopEmail", userEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    Log.d("TAG", "am searching for this user $userId")
                    val documentData = document.data
                    Log.d("TAG", "found this user ${document.id}")
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
                        documentData.get("email").toString(),

                        )

                    fillXml(workShop)


                }

            }.addOnFailureListener {
                Toast.makeText(context,"خطا",Toast.LENGTH_LONG).show()
            }
    }

    fun fillXml(workshop:WorkShop){
        workshopName.setText(workshop.name)
        workshopCity.setText(workshop.city)
        workshopPhone.setText(workshop.phoneNumber)
        workshopShours.setText(workshop.openingHours)
        workshopEhours.setText(workshop.closingHours)
        workshopSDay.setText(workshop.startWorkingDay)
        workshopEDay.setText(workshop.lastWorkingDay)
        workshopDescription.setText(workshop.detail)
        Picasso.get().load(workshop.img).resize(1100,700).placeholder(R.drawable.carsflat).into(workshopImg)

    }

    fun add(workshop: WorkShop) {
        val db = Firebase.firestore

        db.collection("Workshop_waitingList")
            .add(workshop)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم رفع الطلب بنجاح ",Toast.LENGTH_LONG).show()
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"حدث خطأ اثناء رفع طلبك الرجاء المحاولة لاحقا",Toast.LENGTH_LONG).show()
                Log.w("TAG", "Error adding document", e)
            }
    }
}