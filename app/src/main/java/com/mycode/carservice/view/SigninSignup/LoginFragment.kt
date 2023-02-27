package com.mycode.carservice.view.SigninSignup

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
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
import com.mycode.carservice.databinding.FragmentLogInBinding
import com.mycode.carservice.model.userEmail
import com.mycode.carservice.model.userId
import com.mycode.carservice.model.userName
import com.mycode.carservice.view.Customer.MainActivity
import com.mycode.carservice.view.TransporterOwner.TransporterMainActivity
import com.mycode.carservice.view.WorkshopOwner.WorkshopMainActivity


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLogInBinding
    val db = Firebase.firestore
    lateinit var sharedPreferences: SharedPreferences
    var email = ""
    var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogInBinding.inflate(layoutInflater)

        Log.d("TAGTAG","$userId")
            // save prefrence
        if (email.isNotEmpty()&&password.isNotEmpty()) {

            binding.emailTxt.setText(email)
            binding.passTxt.setText(password)
            Log.d("TAG123", "onCreate $email")
            Log.d("TAG123", "onCreate $password")
        }



         sharedPreferences= requireActivity().getSharedPreferences("email", MODE_PRIVATE)

        Log.d("TAG123", "sharedPreferencesEmail $sharedPreferences")
        // shared prefrence
        var emailSP = sharedPreferences.getString("email","")
        var passSP = sharedPreferences.getString("pas","")
        if (emailSP.toString().isNotEmpty()&&passSP.toString().isNotEmpty()) {

            binding.emailTxt.setText(emailSP)
            binding.passTxt.setText(passSP)
            Log.d("TAG123", "onCreate $email")
            Log.d("TAG123", "onCreate $password")
        }


        binding.logBtn.setOnClickListener {
            if (binding.emailTxt.text.isNotEmpty() && binding.passTxt.text.isNotEmpty()) {
//                userCheck(binding.emailTxt.text.toString(), binding.passTxt.text.toString())
                db.collection("Users")
                    .whereEqualTo("email", binding.emailTxt.text.toString())
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val documentData = document.data
                            Log.d("TAG", "${document.id} => ${document.data}")
                            val fbPassword = documentData.get("password").toString()

                            if (binding.passTxt.text.toString() == fbPassword) {
                                val loginType = documentData.get("loginType").toString()
                                userId = document.id
                                userName =documentData.get("username").toString()
                                userEmail =documentData.get("email").toString()
                                email = binding.emailTxt.text.toString()
                                password = binding.passTxt.text.toString()

                                sharedPreferences.edit().putString("email",binding.emailTxt.text.toString()).apply()
                                sharedPreferences.edit().putString("pas",binding.passTxt.text.toString()).apply()
                                sharedPreferences.edit().putString("loginType",loginType).apply()
//                                sharedPreferences.edit().apply()

                                when (loginType) {
                                    "عميل" -> {
                                        val intent = Intent(requireActivity(), MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    "ورشة" -> {
                                        val intent = Intent(requireActivity(), WorkshopMainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    "ناقلة" -> {
                                        val intent = Intent(requireActivity(), TransporterMainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "الرقم او الاسم غير صحيح",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                    .addOnFailureListener { exception ->
                        Log.w("TAG", "Error getting documents.", exception)
                        Toast.makeText(
                            requireContext(),
                            "الرقم او الاسم غير صحيح",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

            } else {
                Toast.makeText(requireContext(), "الرجاء ادخال الرقم السري", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.signUpBtn.setOnClickListener {
            loadFragment(SignUpFragment())
        }


        // Inflate the layout for this fragment
        return binding.root
    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("email", email)
        outState.putString("password", password)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            email = savedInstanceState.getString("email", "")
            password = savedInstanceState.getString("password", "")
        }
    }


}