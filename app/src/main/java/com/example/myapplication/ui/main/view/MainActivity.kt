package com.example.myapplication.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.ui.main.view.fragment.CartFragment
import com.example.myapplication.ui.main.view.fragment.ServiceFragment
import com.example.myapplication.utils.Shared_Common_Pref
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var bottomNavigation: BottomNavigationView? = null
    private lateinit var mServiceFragment: ServiceFragment
    private lateinit var mCartFragment: CartFragment

    private lateinit var sharedCommonPref: Shared_Common_Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        mServiceFragment = ServiceFragment()
        mCartFragment = CartFragment()
        sharedCommonPref = Shared_Common_Pref(this)

        bottomNavigation?.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.page_1 -> {
                    openFragment(mServiceFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.page_2 -> {
                    if (sharedCommonPref.getvalue("cart_group_data").equals(""))
                        Toast.makeText(this, "Your Cart is empty...", Toast.LENGTH_SHORT).show()
                    else
                        openFragment(mCartFragment)
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
        openFragment(mServiceFragment)

    }

    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


    override fun onBackPressed() {

        if (mServiceFragment.isAdded) {
            if (mServiceFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                mServiceFragment.getChildFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }

    }


}



