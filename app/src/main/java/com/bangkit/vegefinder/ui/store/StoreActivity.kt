package com.bangkit.vegefinder.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.bangkit.vegefinder.ui.store.fragment.DashboardFragment
//import com.bangkit.vegefinder.Order
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.databinding.ActivityStoreBinding
import com.bangkit.vegefinder.ui.utils.UtilDevelopmentFragment

//import com.bangkit.vegefinder.Transaction

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(DashboardFragment())

        binding.appbar.btnBack.setOnClickListener{
            onBackPressed()
        }

        binding.bottomStoreNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.dashboard -> replaceFragment(DashboardFragment())
                R.id.order -> replaceFragment(UtilDevelopmentFragment())
                R.id.transaction -> replaceFragment(UtilDevelopmentFragment())
            }
            true
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_cart -> {
//                replaceFragment(UtilDevelopmentFragment())
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}