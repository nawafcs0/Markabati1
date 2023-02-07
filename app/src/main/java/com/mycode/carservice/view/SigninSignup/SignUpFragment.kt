package com.mycode.carservice.view.SigninSignup

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
import com.mycode.carservice.databinding.FragmentSignUpBinding
import com.mycode.carservice.model.User


class SignUpFragment : Fragment() {
 lateinit var binding: FragmentSignUpBinding
    val db = Firebase.firestore
    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.signUpBtn.setOnClickListener {
            if (binding.city.text.isNotEmpty()&&binding.emailTxt.text.isNotEmpty()
                && binding.pass1Txt.text.isNotEmpty() && binding.pass2Txt.text.isNotEmpty() &&
                    binding.usernameTxt.text.isNotEmpty()) {

                if (binding.pass1Txt.text.toString() ==  binding.pass2Txt.text.toString()){

                    db.collection("Users")
                        .whereEqualTo("email",binding.emailTxt.text.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            if (result.isEmpty) {

                                addNewUser(binding.usernameTxt.text.toString(), binding.emailTxt.text.toString(),
                                    binding.pass1Txt.text.toString(),binding.city.text.toString())

                            }
                            else{
                                Toast.makeText(requireContext(), "المستخدم موجود مسبقا!", Toast.LENGTH_SHORT).show()
                            }

                        }

                }else{
//                    Log.d("TAG", "pass is not Unique")
                    //Toast.makeText(requireContext(),"الرقم السري غير متطابق", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(),"الرجاء ادخال السري", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    private fun addNewUser(userName: String, email: String, password: String, city : String) {
        val newUser = User(userName,city,password,email)
        Log.d("TAG", "add user function")
        // Add a new document with a generated ID
        db.collection("Users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                loadFragment(LoginFragment())
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }

    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}