package com.ranzan.securityfeed.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.ActivityHomeBinding
import com.ranzan.securityfeed.view.fragments.DetailedFragment
import com.ranzan.securityfeed.view.fragments.HomeFragment
import com.ranzan.securityfeed.view.fragments.PostFragment
import com.ranzan.securityfeed.view.fragments.ProfileFragment
import com.ranzan.securityfeed.view.listner.OnClickProfile


class HomeActivity : AppCompatActivity(), OnClickProfile {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()


        binding.bottomNavigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.bn_home -> {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()
                        return true
                    }
                    R.id.bn_profile -> {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, ProfileFragment()).commit()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun addPost() {
        fragmentManager.beginTransaction().add(R.id.fragment_container, PostFragment()).addToBackStack("post").commit()

    }

    override fun detailedView() {
        fragmentManager.beginTransaction().add(R.id.fragment_container_1, DetailedFragment()).addToBackStack("detailedView").commit()
        binding.bottomNavigation.visibility=View.INVISIBLE
    }

    override fun detailedBackPressed() {
        binding.bottomNavigation.visibility=View.VISIBLE
    }

}