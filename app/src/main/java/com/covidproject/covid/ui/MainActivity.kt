package com.covidproject.covid.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.covidproject.covid.R
import com.covidproject.covid.databinding.ActivityMainBinding
import com.covidproject.covid.model.data.Items
import com.covidproject.covid.model.data.Vaccine
import com.covidproject.covid.viewmodel.RetrofitViewModel
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private val retrofitViewModel: RetrofitViewModel by viewModels()
    var covidDataList: Items? = null // 코로나 검사 리스트
    var vaccineDataList: List<Vaccine>? = null // 예방접종 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        callVaccine()
    }

    private fun callVaccine() {
        retrofitViewModel.getVaccine()

        retrofitViewModel.vaccineLiveData.observe(this) {
            if (it.data != vaccineDataList) vaccineDataList = it.data

            val mapFragment = MapFragment(this,vaccineDataList!!)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, mapFragment)
                .commit()

            binding.navigationView.setOnItemSelectedListener(this)

            Log.d("TAG", it.toString())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.mapItem -> {
                val mapFragment = MapFragment(this,vaccineDataList!!)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, mapFragment)
                    .commit()
                return true
            }
            R.id.listItem -> {
                val listFragment = ListFragment(vaccineDataList!!)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, listFragment)
                    .commit()
                return true
            }

        }
        return false
    }

    private fun callCoivdTest() {
        retrofitViewModel.getCovidTest()
        retrofitViewModel.covidTestLiveData.observe(this) {
            if (it.body.items != covidDataList) covidDataList = it.body.items

            Log.d("TAG", it.body.items.item.toString())
        }
    }
}