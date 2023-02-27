package com.mycode.carservice.view.Transportation

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
import com.mycode.carservice.databinding.FragmentAddTransporterBinding
import com.mycode.carservice.model.TransporterTable

class AddTransporterFragment : Fragment() {
    lateinit var binding: FragmentAddTransporterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTransporterBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            loadFragment(CarTransporterFragment())
        }

        var name = binding.nameTxt.text
        var city = binding.cityTxt.text
        var type = binding.typeTxt.text
        var price = binding.priceTxt.text
        var phone = binding.phoneNumber.text

        binding.saveBtn.setOnClickListener {
            if (name.isNotEmpty()&&city.isNotEmpty()&&type.isNotEmpty()&&price.isNotEmpty()&&
                phone.isNotEmpty()){
                var rate = ArrayList<Int>()
                rate.add(5)
                add(TransporterTable(
                    "",
                    name.toString(),
                    city.toString(),
                    price.toString(),
                    rate,
                    phone.toString(),
                    ""
                ))
                loadFragment(CarTransporterFragment())

            }else{
                Toast.makeText(requireContext(),"Pls fill all the Info", Toast.LENGTH_SHORT).show()
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }
    fun add(Transporter: TransporterTable) {
        val db = Firebase.firestore

        db.collection("Transporter")
            .add(Transporter)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context,"تم رفع الطلب بنجاح ",Toast.LENGTH_LONG).show()
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"حدث خطأ, حاول مرة اخرى",Toast.LENGTH_LONG).show()

                Log.w("TAG", "Error adding document", e)
            }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}