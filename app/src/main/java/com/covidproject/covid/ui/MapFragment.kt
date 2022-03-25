package com.covidproject.covid.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.covidproject.covid.databinding.FragmentMapBinding
import com.covidproject.covid.model.data.Vaccine
import com.covidproject.covid.viewmodel.RetrofitViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class MapFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentMapBinding
    private var dataList: List<Vaccine>? = null


    private val mapViewModel : RetrofitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}