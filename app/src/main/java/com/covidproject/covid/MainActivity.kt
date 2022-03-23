package com.covidproject.covid

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.covidproject.covid.covidtest.Item
import com.covidproject.covid.covidtest.RetrofitClient
import com.covidproject.covid.databinding.ActivityMainBinding
import com.covidproject.covid.vaccine.api.RetrofitObject
import com.covidproject.covid.vaccine.data.Vaccine
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io


class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {
    var TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding

    private var disposable: Disposable? = null // subscribe 호출 후 Disposable 객체반환
    private var itemList: List<Item>? = null

    private var dataList: List<Vaccine>? = null
    private var perPage = 284

    //Google Map
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient //  현재 위치 가져오기 위한 변수
    private lateinit var mLocationRequest: LocationRequest
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mLocationRequest = LocationRequest.create().apply {
            interval = 2000 // 업데이트 간격 단위
            fastestInterval = 1000 // 가장 빠른 업데이트 간격
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
            maxWaitTime = 2000 // 위치 갱신 요청 최대 대기 시간간
        }

        var locationBuilder = LocationSettingsRequest.Builder()
        locationBuilder.addLocationRequest(mLocationRequest)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCovidTest()
        getVaccine()


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "startLocationUpdates() 두 위치 권한중 하나라도 없는 경우 ")
            return
        }
        Log.d(TAG, "startLocationUpdates() 위치 권한이 하나라도 존재하는 경우")
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청합니다.
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        setDefaultLocation()

        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        // 위치 퍼미션 체크
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED)
                startLocationUpdate()


        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.429571, 126.703271))
                .title("Marker")
        )
    }

    private fun setDefaultLocation() {
        googleMap.apply {
            addMarker(
                MarkerOptions()
                    .position(LatLng(37.429571, 126.703271))
                    .title("Marker")
            )
            moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(37.429571, 126.703271), 10F
                )
            )
            //cameraPosition.zoom
        }

    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun startLocationUpdate() {
        if (!checkLocationServicesStatus()) {
            //다이얼로그로 팝업 띄어주기 권한 없으면
        } else {
            val hasFineLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            val hasCoarseLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()!!
            )

            if (checkPermission())
                googleMap.isMyLocationEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        if(checkPermission()){
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,Looper.myLooper()!!)
        }
    }

    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }


    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d(TAG, "onLocationResult()")
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        Log.d(TAG, "onLocationChanged()")
        mLastLocation = location
    }

    // 정보불러오기
    private fun getCovidTest() {
        disposable = RetrofitClient.getApiService().getInfo(1, 100, "99")
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d("MainActivity-coivdtest", "Success")
                if (it.body.totalCount == perPage) perPage = it.body.totalCount
                if (it.body.items.item != itemList) itemList = it.body.items.item
                Log.d("MainActivity", it.body.items.item.toString())

            }, {
                Log.d("MainActivity-coivdtest", it.message.toString())
            })
    }

    // 정보불러오기
    private fun getVaccine() {
        disposable = RetrofitObject.getApiService().getInfo(1, perPage)
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d(TAG, "Success")
                if (it.totalCount == perPage) perPage = it.totalCount
                if (it.data != dataList) dataList = it.data
                Log.d(TAG, it.totalCount.toString())
                Log.d(TAG, dataList.toString())
                for(num in dataList!!.indices){
                    googleMap.addMarker(MarkerOptions()
                        .position(LatLng(dataList!![num].lat.toDouble(), dataList!![num].lng.toDouble()))
                        .title(dataList!![num].facilityName))
                }

            }, {
                Log.d(TAG, "Fail")
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        //메모리 누수 방지를 위해 사용 후 반드시 자원해제
        disposable?.let {
            disposable!!.dispose() //Observable이 더 이상 데이터를 발행하지 않도록 구독 해지}
        }
    }


}