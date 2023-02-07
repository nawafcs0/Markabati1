package com.mycode.carservice.view.SigninSignup

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mycode.carservice.R
import com.mycode.carservice.view.SigninSignup.LoginFragment

class SignInSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_sign_up)
        loadFragment(LoginFragment())

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = (this as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}