package com.mycode.carservice.view.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mycode.carservice.R
import com.mycode.carservice.model.userId
import com.mycode.carservice.view.WorkshopOwner.AcceptedReqFragment
import com.mycode.carservice.view.WorkshopOwner.WorkshopAccountFragment
import com.mycode.carservice.view.WorkshopOwner.WorkshopRequestsFragment

class AdminMainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        loadFragment(SparePartsFragment())

        bottomNav = findViewById(R.id.bottomNav_admin)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.admin_spareParts -> {
                    loadFragment(SparePartsFragment())
                    true
                }
                R.id.admin_requests -> {
                    loadFragment(AdminRequestFragment())
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
        transaction.replace(R.id.admin_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

}