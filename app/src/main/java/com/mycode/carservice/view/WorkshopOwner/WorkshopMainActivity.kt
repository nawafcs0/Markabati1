package com.mycode.carservice.view.WorkshopOwner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mycode.carservice.R
import com.mycode.carservice.model.userId
import com.mycode.carservice.view.Customer.HomeFragment
import com.mycode.carservice.view.Customer.RequestsFragment

class WorkshopMainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_main)


        loadFragment(AcceptedReqFragment())

        bottomNav = findViewById(R.id.bottomNav_workshop)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.workshop_acceptedReq -> {
                    loadFragment(AcceptedReqFragment())
                    true
                }
                R.id.workshop_requests -> {
                    loadFragment(WorkshopRequestsFragment())
                    true
                }
                R.id.workshop_account -> {
                    loadFragment(WorkshopAccountFragment())
                    true
                }

                else -> {
                    loadFragment(AcceptedReqFragment())
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
        transaction.replace(R.id.workshopOwner_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



}