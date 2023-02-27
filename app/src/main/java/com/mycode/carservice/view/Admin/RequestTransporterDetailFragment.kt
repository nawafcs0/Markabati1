package com.mycode.carservice.view.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.databinding.FragmentRequestTransporterDetailBinding
import com.mycode.carservice.model.TransporterTable
import com.mycode.carservice.model.WorkShop


private const val ARG_PARAM1 = "transporter"



class RequestTransporterDetailFragment : Fragment() {
    private lateinit var param1: TransporterTable
    private var param2: String? = null
    lateinit var binding:FragmentRequestTransporterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as TransporterTable
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentRequestTransporterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adminTransporterReqName.text=param1.name
        binding.adminTransporterReqPhone.text=param1.transporterPhoneNumber
        binding.adminTransporterReqPrice.text=param1.price
        binding.adminTransporterReqCity.text=param1.city

        binding.adminTransporterReqBtnAccept.setOnClickListener {
            add(param1)
            deleteWaitinglist(param1)
        }
        binding.adminTransporterReqBtnReject.setOnClickListener {
            rejectTransporter(param1)
        }

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

    fun add(Transporter: TransporterTable) {
        val db = Firebase.firestore

        db.collection("Transporter")
            .add(Transporter)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم الموافقة", Toast.LENGTH_LONG).show()
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"حدث خطأ, حاول مرة اخرى", Toast.LENGTH_LONG).show()

                Log.w("TAG", "Error adding document", e)
            }
    }
    private fun rejectTransporter(workShop: TransporterTable) {
        val db = Firebase.firestore

        db.collection("Workshop_waitingList")
            .document(param1.transporterId).delete()
            .addOnSuccessListener{
                Toast.makeText(context,"تم رفض الطلب",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }
    private fun deleteWaitinglist(transporter: TransporterTable) {
        val db = Firebase.firestore

        db.collection("Transporter_waitingList")
            .document(param1.transporterId).delete()
            .addOnSuccessListener{

            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }

}