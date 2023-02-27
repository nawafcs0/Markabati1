package com.mycode.carservice.view.TransporterOwner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.mycode.carservice.databinding.FragmentTransporterReqdetailBinding
import com.mycode.carservice.model.TransporterOwnerModels.AcceptedReqTransporter
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter


private const val ARG_PARAM1 = "requestDetails"



class TransporterReqDetailFragment : Fragment() {
     lateinit var param1:CustomerReqTransporter

    lateinit var binding: FragmentTransporterReqdetailBinding




    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as CustomerReqTransporter

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransporterReqdetailBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment

       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.transporterCustomerReqDetailsName.text=param1.customerName
        binding.transporterCustomerReqDetailsPhone.text=param1.customerPhone
        binding.transporterCustomerReqDetailsStartLocation.text=param1.from
        binding.transporterCustomerReqDetailsEndLocation.text=param1.to

        var carName =binding.transporterCustomerReqDetailsName.text.toString()
        var phone =binding.transporterCustomerReqDetailsPhone.text.toString()
        var from =binding.transporterCustomerReqDetailsStartLocation.text.toString()
        var to =binding.transporterCustomerReqDetailsEndLocation.text.toString()




        binding.transporterCustomerReqDetailsAccept.setOnClickListener{
            //
            if (carName.isNotEmpty()&&phone.isNotEmpty()&&from.isNotEmpty()&&to.isNotEmpty()){
               add(AcceptedReqTransporter(
                   param1.customerName,
                   param1.carName,
                   param1.customerPhone,
                   "مقبول",
                   param1.transporterEmail,
                   param1.from,
                   param1.to
               ))


            }else{
                Toast.makeText(requireContext(),"جميع الحقول مطلوبة", Toast.LENGTH_SHORT).show()
            }
        }
        binding.transporterCustomerReqDetailsReject.setOnClickListener {
            rejectCustomerReq()
        }






    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransporterReqDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }


    fun add(request: AcceptedReqTransporter) {
        val db = Firebase.firestore
        //TODO get the accepted request in accepted requests fragment
        db.collection("transporter_acceptedReq")
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
    fun deleteCustomerRequestList(request: AcceptedReqTransporter){
        val db = Firebase.firestore
        db.collection("transporter_customerReq")
            .document(param1.requestId)
            .delete().addOnSuccessListener {
                Log.d("TAG","Doc deleted")
            }.addOnFailureListener {
                Log.d("TAG","{${it.message}}")
            }
    }
    private fun rejectCustomerReq() {
        val db = Firebase.firestore

        db.collection("transporter_customerReq")
            .document(param1.requestId).delete()
            .addOnSuccessListener{
                Toast.makeText(context,"تم رفض الطلب",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }



//    fun fillXml(workshop:WorkShop){
//        workshopName.setText(workshop.name)
//        workshopCity.setText(workshop.city)
//        workshopPhone.setText(workshop.phoneNumber)
//        workshopShours.setText(workshop.openingHours)
//        workshopEhours.setText(workshop.closingHours)
//        workshopSDay.setText(workshop.startWorkingDay)
//        workshopEDay.setText(workshop.lastWorkingDay)
//        workshopDescription.setText(workshop.detail)
//        Picasso.get().load(workshop.img).resize(1100,700).placeholder(R.drawable.carsflat).into(workshopImg)
//
//    }

}