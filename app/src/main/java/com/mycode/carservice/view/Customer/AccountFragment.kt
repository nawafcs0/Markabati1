package com.mycode.carservice.view.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.databinding.FragmentAccountBinding
import com.mycode.carservice.model.User
import com.mycode.carservice.model.WorkShop
import com.mycode.carservice.view.Workshop.WorkShopDetailsFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "userId"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment() {
    private var userId: String? = null
    lateinit var binding: FragmentAccountBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        Log.d("TAG","$userId")
        binding.backBtn.setOnClickListener {
            loadFragment(HomeFragment())
        }
        datainit()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun datainit() {
        db.collection("Users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    Log.d("TAG","am searching for this user $userId")
                    if (document.id == userId){
                        val documentData = document.data
                        Log.d("TAG","found this user ${document.id}")
                        val profile = User(
                        documentData.get("username").toString(),
                        documentData.get("city").toString(),
                        documentData.get("password").toString(),
                        documentData.get("email").toString(),
                        documentData.get("loginType").toString(),

                    )
                        fillXml(profile)
//                        loadDetails(profile.username)

                    }


                    Log.d("TAGTAG", "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
    fun fillXml(user:User){
        binding.usernameTxt.text = user.username
        binding.cityTxt.text = user.city
        binding.emailTxt.text = user.email
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun loadDetails(userName: String) {
        val bundle = Bundle()
//        bundle.putInt("workShop", position)
        bundle.putString("userName",userName)
        val fragment = RequestWorkshopFragment()
        fragment.arguments = bundle
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(com.mycode.carservice.R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}