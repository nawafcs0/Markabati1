package com.mycode.carservice.view.Workshop

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mycode.carservice.R
import com.mycode.carservice.databinding.FragmentAddWorkShopBinding
import com.mycode.carservice.model.WorkShop
import com.mycode.carservice.view.Customer.MapsFragment


class AddWorkShopFragment : Fragment() {

    var IMG_REQ_CODE=100
    lateinit var image_url:String
    lateinit var imgUri: Uri
    lateinit var workShop_img:ImageView
    lateinit var binding: FragmentAddWorkShopBinding
    private var storageRef= FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWorkShopBinding.inflate(layoutInflater)
        binding.fBtn.setOnClickListener {
            loadFragment(MapsFragment())
        }
        var name = binding.nameTxt.text
        var city = binding.cityTxt.text
        var closingH = binding.closingTime.text
        var openH = binding.openTime.text
        var detail = binding.detailTxt.text
        var phone = binding.phoneNumber.text
        var openD = binding.openDay.text
        var closeD = binding.closeDay.text
        workShop_img=binding.addingWorkshopImg



        workShop_img.setOnClickListener(View.OnClickListener {
            //TODO get the image from the user device
            pickImage()
        })



        binding.saveBtn.setOnClickListener {
            if (name.isNotEmpty()&&city.isNotEmpty()&&closingH.isNotEmpty()&&openH.isNotEmpty()&&detail.isNotEmpty()&&
                phone.isNotEmpty()&&openD.isNotEmpty()&&closeD.isNotEmpty()){
                var rating = ArrayList<Int>()
//                rating.add(true)
                //TODO adding workshop image from this page to DB
                add(WorkShop(
                    "",
                    city.toString(),
                    closingH.toString(),
                    detail.toString(),
                    ""
                    ,
                    closeD.toString(),
                    name.toString(),
                    openH.toString(),
                    rating,
                    closeD.toString(),
                    phone.toString(),
                    ""
                ))
                loadFragment(WorkshopFragment())

//                storageRef.child("workshop/Image${System.currentTimeMillis()}").putFile(imgUri)
//                    .addOnSuccessListener {
//                        storageRef.downloadUrl.addOnSuccessListener {uri ->
//
//                            add(WorkShop(city.toString(),closingH.toString(),detail.toString(),image_url,closeD.toString()
//                                ,name.toString(),openH.toString(),rating,closeD.toString(),phone.toString()))
//                            loadFragment(WorkshopFragment())
//                        }
//
//                    }




            }else{
                Toast.makeText(requireContext(),"رجاءا قم بتعبئة جميع الحقول!",Toast.LENGTH_SHORT).show()
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun pickImage(){
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent,IMG_REQ_CODE)
    }

    fun add(workShop: WorkShop) {
        val db = Firebase.firestore

        db.collection("workShops")
            .add(workShop)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==IMG_REQ_CODE && resultCode== RESULT_OK){
            imgUri= data?.data!!
            //upload image uri to the firebase storage
            workShop_img.setImageURI(data.data)

        }
    }


}