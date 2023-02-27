package com.mycode.carservice.view.Customer

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import com.mycode.carservice.model.WorkshopOwnerModels.CustomerReqWorkshop
import com.mycode.carservice.model.userId
import com.mycode.carservice.model.userName


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "userName"
private const val ARG_PARAM2 = "workshop"

class RequestWorkshopFragment : Fragment() {
    private lateinit var param1: String
    private lateinit var param2: WorkShop

    private lateinit var customerCar: TextView
    private lateinit var customerPhone: TextView
    private lateinit var customerProblem: TextView
    private lateinit var btnRequest: Button
    val db = Firebase.firestore

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1).toString()
            param2 = it.getSerializable(ARG_PARAM2) as WorkShop
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reqeust_workshop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerCar=view.findViewById(R.id.CustomerReqDetails_carName)
        customerPhone=view.findViewById(R.id.CustomerReqDetails_phone)
        customerProblem=view.findViewById(R.id.CustomerReqDetails_desc)
        btnRequest=view.findViewById(R.id.CustomerReqBtn)

        sharedPreferences= requireActivity().getSharedPreferences("email", MODE_PRIVATE)



        btnRequest.setOnClickListener{
            if (customerPhone.text.isNotEmpty()&&customerCar.text.isNotEmpty()
                &&customerProblem.text.isNotEmpty()) {
                uploadRequest(
                    CustomerReqWorkshop(
                        userId,
                        userName,
                        customerCar.text.toString(),
                        customerPhone.text.toString(),
                        customerProblem.text.toString(),
                        param2.workshopEmail,
                        param2.name

                    )
                )
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestWorkshopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun uploadRequest(customerReq:CustomerReqWorkshop) {
        db.collection("workshop_customerReq")
            // .whereEqualTo("city", globalCity)
            .add(customerReq)
            .addOnSuccessListener {
                db.collection("SparePartsRequests")
                    .add(customerReq).addOnSuccessListener {
                        Log.d("TAG", "onViewCreated: ${it.id}")
                        Toast.makeText(requireContext(), "تم الطلب بنجاح!", Toast.LENGTH_SHORT).show()

                    }
                Toast.makeText(context,"تم رفع طلبك بنجاح",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context,"حدث خطأ حاول مرة أخرى",Toast.LENGTH_SHORT).show()
            }


    }

}