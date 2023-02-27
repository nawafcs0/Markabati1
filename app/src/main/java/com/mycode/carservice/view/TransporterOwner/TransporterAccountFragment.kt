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
import com.mycode.carservice.databinding.FragmentTransporterAccountBinding
import com.mycode.carservice.model.*
import com.mycode.carservice.model.TransporterOwnerModels.AcceptedReqTransporter
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TransporterAccountFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var db = Firebase.firestore
    lateinit var binding: FragmentTransporterAccountBinding

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
        binding = FragmentTransporterAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO ثبت ببيانات السطحة بعد رفع الطلب الى الادمن
        datainita()

        binding.saveBtn.setOnClickListener{
            //
            var rate = ArrayList<Int>()
            rate.add(5)
            add(TransporterTable(
                "",
                binding.nameTxt.text.toString(),
                binding.cityTxt.text.toString(),
                binding.priceTxt.text.toString(),
                rate,
                binding.phoneNumber.text.toString(),
                userEmail,

            ))


            }
        }


    private fun datainita() {
        db = Firebase.firestore

        db.collection("Transporter")
            .whereEqualTo("transporterEmail", userEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    Log.d("TAG", "am searching for this user $userId")
                    val documentData = document.data
                    Log.d("TAG", "found this user ${document.id}")
                    val transporter = TransporterTable(
                        document.id,
                        documentData.get("name").toString(),
                        documentData.get("city").toString(),
                        documentData.get("price").toString(),
                        documentData.get("rate") as ArrayList<Int> /* = java.util.ArrayList<kotlin.Int> */,
                        documentData.get("transporterPhoneNumber").toString(),
                        documentData.get("transporterEmail").toString(),


                        )

                    fillXml(transporter)

                }

            }.addOnFailureListener {
                Toast.makeText(context,"خطا",Toast.LENGTH_LONG).show()
            }
    }

    fun fillXml(transporter:TransporterTable){
        binding.nameTxt.setText(transporter.name)
        binding.cityTxt.setText(transporter.city)
        binding.phoneNumber.setText(transporter.transporterPhoneNumber)
        binding.priceTxt.setText(transporter.price)

    }

    fun add(Transporter: TransporterTable) {
        val db = Firebase.firestore

        db.collection("Transporter_waitingList")
            .add(Transporter)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم رفع الطلب بنجاح ",Toast.LENGTH_LONG).show()
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context," حدث خطأ حاول مرة اخرى",Toast.LENGTH_LONG).show()
                Log.w("TAG", "Error adding document", e)
            }
    }



    }

//TODO get the transporter account details


