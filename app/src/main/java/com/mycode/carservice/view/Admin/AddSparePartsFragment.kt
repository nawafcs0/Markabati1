package com.mycode.carservice.view.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.databinding.FragmentAddSparePartsBinding
import com.mycode.carservice.view.Spareparts.SparePartsFragment


class AddSparePartsFragment : Fragment() {
    lateinit var binding : FragmentAddSparePartsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSparePartsBinding.inflate(layoutInflater)
        var manufacturer = binding.manufacturer.text
        var price = binding.priceTxt.text
        var carModel = binding.carModel.text
        var city = binding.cityTxt.text
        var daysToDeliver = ""
        var img = ""
        var partName = binding.partNameTxt.text
        var type = binding.typeTxt.text


        binding.saveBtn.setOnClickListener {
            if (manufacturer.isNotEmpty()&&price.isNotEmpty()&&carModel.isNotEmpty()&&partName.isNotEmpty()&&type.isNotEmpty()){

                //TODO adding workshop image from this page to DB
                add()

            }else{
                Toast.makeText(requireContext(),"رجاءا قم بتعبئة جميع الحقول!", Toast.LENGTH_SHORT).show()
            }

        }

        // Inflate the layout for this fragment
        return  binding.root
    }

    fun add() {
        var manufacturer = binding.manufacturer.text
        var price = binding.priceTxt.text
        var carModel = binding.carModel.text.toString()
        var city = binding.cityTxt.text
        var daysToDeliver = ""
        var permaLink = ""
        var partName = binding.partNameTxt.text
        var type = binding.typeTxt.text
        val db = Firebase.firestore
        var sparePart = hashMapOf(
            "Manufacturer" to manufacturer.toString(),
            "Price" to price.toString(),
            "carModel" to carModel.toString(),
            "city" to city.toString(),
            "daysToDeliver" to "6",
            "img" to permaLink.toString(),
            "partName" to partName.toString(),
            "type" to type.toString(),
        )
        db.collection("SpareParts")
            .add(sparePart)
            .addOnSuccessListener { documentReference ->
                loadFragment(com.mycode.carservice.view.Admin.SparePartsFragment())
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.admin_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
