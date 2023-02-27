package com.mycode.carservice.view.TransporterOwner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mycode.carservice.R
import com.mycode.carservice.model.userId
import com.mycode.carservice.view.Customer.HomeFragment
import com.mycode.carservice.view.Customer.RequestsFragment

class TransporterMainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transporter_main)


        loadFragment(AcceptedTransporterReqFragment())

        bottomNav = findViewById(R.id.bottomNav_transporter)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.transporter_acceptedReq -> {
                    loadFragment(AcceptedTransporterReqFragment())
                    true
                }
                R.id.transporter_requests -> {
                    loadFragment(TransporterRequestsFragment())
                    true
                }
                R.id.transporter_account -> {
                    loadFragment((TransporterAccountFragment()))
                    true
                }

                else -> {
//                    loadFragment(())
                    true
                }
            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString("userId", userId)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.transporterOwner_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



}