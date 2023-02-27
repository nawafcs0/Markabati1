package com.mycode.carservice.view.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.databinding.FragmentRequestTransporterBinding
import com.mycode.carservice.databinding.FragmentRequestTransporterDetailBinding
import com.mycode.carservice.model.CarTransporter
import com.mycode.carservice.model.TransporterOwnerModels.CustomerReqTransporter
import com.mycode.carservice.model.WorkshopOwnerModels.CustomerReqWorkshop
import com.mycode.carservice.model.userId
import com.mycode.carservice.model.userName

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "transporter"
private const val ARG_PARAM2 = "param2"


class RequestTransporterFragment : Fragment() {
    private lateinit var param1: CarTransporter
    private var param2: String? = null
    lateinit var binding: FragmentRequestTransporterBinding

    val db=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as CarTransporter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRequestTransporterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.transporterReqBtnUpload.setOnClickListener {
            uploadRequest(
                CustomerReqTransporter(
                    userId,
                    userName,
                    binding.transporterReqEtCarName.text.toString(),
                    binding.transporterReqEtPhone.text.toString(),
                    binding.transporterReqEtFrom.text.toString(),
                    binding.transporterReqEtTo.text.toString(),
                    param1.transporterEmail,
                    param1.name,
                )
            )
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestTransporterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun uploadRequest(customerReq: CustomerReqTransporter) {
        db.collection("transporter_customerReq")
            // .whereEqualTo("city", globalCity)
            .add(customerReq)
            .addOnSuccessListener {

                Toast.makeText(context,"تم رفع طلبك بنجاح", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context,"حدث خطأ حاول مرة أخرى", Toast.LENGTH_SHORT).show()
            }


    }
}