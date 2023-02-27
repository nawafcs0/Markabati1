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
import com.mycode.carservice.R
import com.mycode.carservice.databinding.FragmentRequestDetailBinding
import com.mycode.carservice.model.WorkShop
import com.squareup.picasso.Picasso


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "workShop"
private const val ARG_PARAM2 = "param2"


class RequestDetailFragment : Fragment() {
    lateinit var binding: FragmentRequestDetailBinding

    lateinit var param1:WorkShop
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as WorkShop
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.requestDetailName.text=param1.name
        binding.requestDetailCity.text=param1.city
        binding.requestDetailPhone.text=param1.phoneNumber
        binding.requestDetailDesc.text=param1.detail
        binding.requestDetailStartDay.text=param1.startWorkingDay
        binding.requestDetailEndDay.text=param1.lastWorkingDay
        binding.requestDetailEndHorus.text=param1.closingHours
        binding.requestDetailStartHorus.text=param1.openingHours
        if (param1.img.isNotEmpty()) {
            Picasso.get().load(param1.img).placeholder(R.drawable.carsflat).into(binding.imgReqDetails)
        }

        binding.requestAcceptBtn.setOnClickListener{
            deleteWaitinglist(param1)
            addWorkshop(param1)


        }
        binding.requestRejectBtn.setOnClickListener {
            rejectWorkshop(param1)
        }





    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun addWorkshop(workShop: WorkShop) {
        val db = Firebase.firestore

        db.collection("workShops")
            .add(workShop)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم اضافة الورشة",Toast.LENGTH_SHORT).show()

                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    private fun rejectWorkshop(workShop: WorkShop) {
        val db = Firebase.firestore

        db.collection("Workshop_waitingList")
            .document(param1.workshopId).delete()
            .addOnSuccessListener{
                Toast.makeText(context,"تم رفض الطلب",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }
    private fun deleteWaitinglist(workShop: WorkShop) {
        val db = Firebase.firestore

        db.collection("Workshop_waitingList")
            .document(param1.workshopId).delete()
            .addOnSuccessListener{
            }.addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }


    }


}