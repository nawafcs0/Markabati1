package com.mycode.carservice.view.Customer


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.model.globalCity
import com.mycode.carservice.model.userId

import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView


    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private lateinit var locationCallback: LocationCallback

    // This will store current location info
    private var currentLocation: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            locationRequest = LocationRequest().apply {
                // Sets the desired interval for
                // active location updates.
                // This interval is inexact.
                interval = TimeUnit.SECONDS.toMillis(60)
                // Sets the fastest rate for active location updates.
                // This interval is exact, and your application will never
                // receive updates more frequently than this value
                fastestInterval = TimeUnit.SECONDS.toMillis(30)
                // Sets the maximum time when batched location
                // updates are delivered. Updates may be
                // delivered sooner than this interval
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            locationCallback = object : LocationCallback() {
                @RequiresApi(33)
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult.lastLocation?.let {
                        currentLocation = it
                        latitude = currentLocation!!.latitude
                        longitude = currentLocation!!.longitude
                        // use latitude and longitude as per your need
                        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())

                            geocoder.getFromLocation(latitude, longitude, 1) { address ->
                                Log.d("TAG", "Location = ${address[0].subAdminArea}")
                                globalCity = address[0].subAdminArea

                            }
                    } ?: {
                        Log.d("TAG", "Location information isn't available.")
                    }
                }
            }


        getLocation()

        // الواجهه الرئيسية اللتي تعمل عند التشغيل
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.requests -> {
                    loadFragment(RequestsFragment())
                    true
                }
                R.id.account -> {
                    loadFragment(AccountFragment())
                    true
                }

                else -> {
                    loadFragment(HomeFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString("userId",userId)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val removeTask = mFusedLocationClient.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TAG", "Location Callback removed.")
            } else {
                Log.d("TAG", "Failed to remove Location Callback.")
            }
        }
    }
}