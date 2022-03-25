package com.covidproject.covid.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.covidproject.covid.R
import com.covidproject.covid.databinding.ActivityMainBinding
import com.covidproject.covid.model.data.Vaccine
import com.covidproject.covid.viewmodel.RetrofitViewModel
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    val retrofitViewModel : RetrofitViewModel by viewModels()

    private var dataList: List<Vaccine>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setOnItemSelectedListener(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, mapFragment)
            .commit()

        //retrofitViewModel.getVaccine()

       /* retrofitViewModel.vaccineLiveData.observe(this, Observer {
            if (it.data != dataList) dataList = it.data
            Log.d("TAG", it.toString())
        })*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mapItem -> {
                val mapFragment = MapFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, mapFragment)
                    .commit()
                return true
            }
            R.id.listItem -> {
                val listFragment = ListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment)
                    .commit()
                return true
            }
        }
        return false
    }
}