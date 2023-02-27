package com.mycode.carservice.view.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mycode.carservice.R
import com.mycode.carservice.model.SparePartsCustomerReq
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "spareParts"



class RequestSpareParteDetailFragment : Fragment() {
    private lateinit var param1: SparePartsCustomerReq
    private var param2: String? = null

    private lateinit var tv_customerName:TextView
    private lateinit var tv_customerEmail:TextView
    private lateinit var tv_sparePartTitle:TextView
    private lateinit var tv_sparePartPrice:TextView
    private lateinit var tv_sparePartlocation:TextView
    private lateinit var tv_sparePartType:TextView
    private lateinit var img_sparePar:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as SparePartsCustomerReq
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spare_parts_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_customerName=view.findViewById(R.id.customer_name)
        tv_customerEmail=view.findViewById(R.id.customer_phone)
        tv_sparePartTitle=view.findViewById(R.id.customerSpare_title)
        tv_sparePartPrice=view.findViewById(R.id.customerSpare_price)
        tv_sparePartlocation=view.findViewById(R.id.customerSpare_location)
        tv_sparePartType=view.findViewById(R.id.customerSpare_type)
        img_sparePar=view.findViewById(R.id.customerImg_spareDetails)


        tv_customerName.text=param1.requesterName
        tv_customerEmail.text=param1.requesterEmail
        tv_sparePartTitle.text=param1.partName
        tv_sparePartType.text=param1.type
        tv_sparePartlocation.text=param1.city

        Picasso.get().load(param1.img).placeholder(R.drawable.spae_part_ic).into(img_sparePar)

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestTransporterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

//    fun add(Transporter: TransporterTable) {
//        val db = Firebase.firestore
//
//        db.collection("Transporter")
//            .add(Transporter)
//            .addOnSuccessListener { documentReference ->
//                Toast.makeText(context,"تم الموافقة", Toast.LENGTH_LONG).show()
//                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(context,"حدث خطأ, حاول مرة اخرى", Toast.LENGTH_LONG).show()
//
//                Log.w("TAG", "Error adding document", e)
//            }
//    }
//    private fun rejectTransporter(workShop: TransporterTable) {
//        val db = Firebase.firestore
//
//        db.collection("Workshop_waitingList")
//            .document(param1.transporterId).delete()
//            .addOnSuccessListener{
//                Toast.makeText(context,"تم رفض الطلب",Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
//
//            }
//
//
//    }
//    private fun deleteWaitinglist(transporter: TransporterTable) {
//        val db = Firebase.firestore
//
//        db.collection("Transporter_waitingList")
//            .document(param1.transporterId).delete()
//            .addOnSuccessListener{
//
//            }.addOnFailureListener {
//                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
//
//            }
//
//
//    }

}