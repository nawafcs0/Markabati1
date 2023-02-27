package com.mycode.carservice.view.WorkshopOwner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.model.WorkShop
import com.mycode.carservice.model.WorkshopOwnerModels.AcceptedReqWorkshop
import com.mycode.carservice.model.WorkshopOwnerModels.CustomerReqWorkshop

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "requestDetails"
private const val ARG_PARAM2 = -1


class WorkshopReqDetailsFragment : Fragment() {
    lateinit var param1: CustomerReqWorkshop
    lateinit var param2: AcceptedReqWorkshop

    private lateinit var customerCar:TextView
    private lateinit var customerPhone:TextView
    private lateinit var customerProblem:TextView
    private lateinit var btnAccept:Button
    private lateinit var btnReject:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

                param1 = it.getSerializable(ARG_PARAM1) as CustomerReqWorkshop


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshop_req_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerCar=view.findViewById(R.id.workshopCustomerReqDetails_name)
        customerPhone=view.findViewById(R.id.workshopCustomerReqDetails_phone)
        customerProblem=view.findViewById(R.id.workshopCustomerReqDetails_desc)
        btnReject=view.findViewById(R.id.workshopCustomerReqDetails_reject)
        btnAccept=view.findViewById(R.id.workshopCustomerReqDetails_accept)

        customerCar.text=param1.carName
        customerPhone.text=param1.customerPhone
        customerProblem.text=param1.description



        btnAccept.setOnClickListener{
         //TODO اقبل الطلب وحدث حالة الطلب عند العميل
            add(
                AcceptedReqWorkshop(
                    param1.customerName,
                    param1.carName,
                    param1.customerPhone,
                    "مقبول",
                    param1.workshopEmail

                )
            )
        }
        btnReject.setOnClickListener{
            //TODO ارفض الطلب وغير حالة الطلب عند العميل
            rejectCustomerReq()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            WorkshopReqDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }

    fun add(request: AcceptedReqWorkshop) {
        val db = Firebase.firestore

        db.collection("workshop_acceptedReq")
            .add(request)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم قبول الطلب",Toast.LENGTH_SHORT).show()
//                deleteCustomerRequestList(request)
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"${e.message}",Toast.LENGTH_SHORT).show()
                Log.w("TAG", "Error adding document", e)
            }

    }
    fun deleteCustomerRequestList(request: AcceptedReqWorkshop){
        val db = Firebase.firestore
        db.collection("workshop_customerReq")
            .document(param1.requestId)
            .delete().addOnSuccessListener {
                Log.d("TAG","Doc deleted")
            }.addOnFailureListener {
                Log.d("TAG","{${it.message}}")
            }
    }
    private fun rejectCustomerReq() {
        val db = Firebase.firestore

        db.collection("workshop_customerReq")
            .document(param1.requestId).delete()
            .addOnSuccessListener{
                Toast.makeText(context,"تم رفض الطلب",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }


}