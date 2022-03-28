package com.covidproject.covid.ui

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.covidproject.covid.databinding.FragmentMapBinding
import com.covidproject.covid.model.data.Vaccine
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment(var mContext: Context, var vaccineList: List<Vaccine>) : Fragment(),
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentMapBinding
    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null
    var initLocation = LatLng(37.57919346914224, 126.8885265170101)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.map.apply {
            getMapAsync(this@MapFragment)
            onCreate(savedInstanceState)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initLocation, 10F))
        //googleMap.addMarker(MarkerOptions().position(initLocation).title("내 위치"))

        for(item in vaccineList){
            var latlng = LatLng(item.lat.toDouble(),item.lng.toDouble())
            Log.d("확인", "아이템 ${item.facilityName}")
            googleMap.addMarker(MarkerOptions().position(latlng).title(item.facilityName))
        }

        // 현 위치 관련
        /*mLocationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager?
        mLocationListener = LocationListener { location ->
            var lat = 0.0
            var lng = 0.0
            if (location != null) {
                lat = location.latitude
                lng = location.longitude
            }
            val currentLocation = LatLng(lat, lng)
            googleMap.addMarker(MarkerOptions().position(currentLocation).title("내 위치"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
        }

        if (ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000L,
                30f,
                mLocationListener!!
            )
        }*/
    }
}
